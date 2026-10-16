package com.mathioks.narutomod.jutsu;

import java.util.*;

/**
 * Motor de combos de teclas C / V / B (estilo Sekwah41 / Naruto Anime Mod 1.20).
 *
 * <p>Algoritmo (replicado do mod de referência, adaptado e testável):
 * <ol>
 *   <li>Tecla C = dígito 1, V = 2, B = 3. Cada pressão é anexada ao buffer.</li>
 *   <li><b>Commit por atraso:</b> se o jogador não pressionar nada por
 *       {@code CAST_DELAY} ticks, o buffer é resolvido como um jutsu completo
 *       (se não existir combo exato, emite falha e limpa o buffer).</li>
 *   <li><b>Jutsus carregados (CHANNELED):</b> se o jogador <i>segurar</i> a última
 *       tecla além de {@code HOLD_THRESHOLD_TICKS} ticks, entra em carga; ao soltar,
 *       o jutsu é liberado com potência proporcional ao tempo carregado.</li>
 * </ol>
 *
 * <p>Zero dependências de Minecraft: pode ser testado em console/JUnit.
 */
public final class ComboEngine {

    public static final int KEY_C = 1, KEY_V = 2, KEY_B = 3;

    /** Ticks de silêncio antes de commitar o combo (0,2 s). Configurável pelo usuário. */
    public static final int CAST_DELAY_TICKS = 4;
    /** Ticks segurando a última tecla para iniciar a carga de um jutsu CHANNELED. */
    public static final int HOLD_THRESHOLD_TICKS = 12;

    public record JutsuDef(String id, String combo, String nome, String nomePt,
                           String rank, String escola, boolean carga, String descricao) {}

    /** Resultado de uma resolução de combo. */
    public record Cast(JutsuDef jutsu, boolean carregado, int ticksCarregados, String combo) {}

    /** Falha de combo (nenhum jutsu com esse código). */
    public record Falha(String combo) {}

    private final Map<String, JutsuDef> porCombo = new HashMap<>();
    private final List<Cast> castes = new ArrayList<>();
    private final List<Falha> falhas = new ArrayList<>();

    private final StringBuilder buffer = new StringBuilder();
    private int ticksDesdeUltimaTecla = 0;
    private int heldTicks = 0;
    private int ticksCarregados = 0;
    private boolean carregando = false;
    private JutsuDef jutsuCarregando = null;

    // -------------------------------------------------------------- registro
    public void registrar(JutsuDef def) { porCombo.put(def.combo(), def); }
    public int totalJutsus() { return porCombo.size(); }
    public Optional<JutsuDef> porCombo(String combo) { return Optional.ofNullable(porCombo.get(combo)); }
    /** Algum combo registrado começa com este prefixo? (só para UI/ajuda) */
    public boolean temPrefixo(String prefixo) {
        return porCombo.keySet().stream().anyMatch(k -> k.startsWith(prefixo) && k.length() > prefixo.length());
    }

    // ----------------------------------------------------------------- input
    public void pressionar(int digito) {
        if (digito < 1 || digito > 3) throw new IllegalArgumentException("digito 1..3");
        buffer.append(digito);
        ticksDesdeUltimaTecla = 0;
        heldTicks = 1;
    }

    /**
     * Informa se a última tecla continua pressionada neste tick.
     * <p>Se o combo atual for de um jutsu {@code carga()}, o motor entra em modo
     * CARREGANDO ao passar de {@link #HOLD_THRESHOLD_TICKS} ticks segurando.
     */
    public void tickSegurando(boolean segurandoUltimaTecla) {
        if (carregando) {                                  // já carregando: conta carga
            if (segurandoUltimaTecla) ticksCarregados++;
            return;
        }
        if (!segurandoUltimaTecla) { heldTicks = 0; return; }
        heldTicks++;
        JutsuDef def = porCombo.get(buffer.toString());
        if (def != null && def.carga() && heldTicks >= HOLD_THRESHOLD_TICKS) {
            carregando = true;
            jutsuCarregando = def;
            ticksCarregados = 0;
            buffer.setLength(0);
        }
    }

    /** Tick geral do cliente (commit por atraso). */
    public void tick() {
        if (carregando) return;
        if (buffer.length() == 0) return;
        JutsuDef atual = porCombo.get(buffer.toString());
        // jutsu de carga NÃO commita por atraso: espera hold (carregar) ou soltar (versão fraca)
        if (atual != null && atual.carga()) return;
        if (++ticksDesdeUltimaTecla > CAST_DELAY_TICKS) {
            if (atual != null) castes.add(new Cast(atual, false, 0, buffer.toString()));
            else falhas.add(new Falha(buffer.toString()));
            buffer.setLength(0);
            ticksDesdeUltimaTecla = 0;
        }
    }

    /** Jogador soltou as teclas de jutsu. */
    public void soltar() {
        if (carregando) {
            castes.add(new Cast(jutsuCarregando, true, ticksCarregados, jutsuCarregando.combo()));
            carregando = false; jutsuCarregando = null; ticksCarregados = 0;
        } else {
            JutsuDef def = porCombo.get(buffer.toString());
            // toque rápido em jutsu de carga = versão fraca (carga mínima)
            if (def != null && def.carga()) {
                castes.add(new Cast(def, true, heldTicks, def.combo()));
                buffer.setLength(0); ticksDesdeUltimaTecla = 0;
            }
        }
        heldTicks = 0;
    }

    /** Cancela tudo (ex.: abriu o menu de jutsus). */
    public void reset() {
        buffer.setLength(0); ticksDesdeUltimaTecla = 0; heldTicks = 0; ticksCarregados = 0;
        carregando = false; jutsuCarregando = null;
    }

    // ------------------------------------------------------------------ saida
    public String bufferAtual() { return buffer.toString(); }
    public boolean isCarregando() { return carregando; }
    public float cargaPercentual() {
        return carregando ? Math.min(1f, ticksCarregados / 40f) : 0f;
    }
    public int ticksCarregados() { return ticksCarregados; }
    public List<Cast> drenarCasts() { var c = List.copyOf(castes); castes.clear(); return c; }
    public List<Falha> drenarFalhas() { var f = List.copyOf(falhas); falhas.clear(); return f; }

    /** "122" -> "C-V-V" */
    public static String teclas(String combo) {
        StringBuilder sb = new StringBuilder();
        for (char c : combo.toCharArray()) {
            if (sb.length() > 0) sb.append('-');
            sb.append(switch (c) { case '1' -> "C"; case '2' -> "V"; case '3' -> "B"; default -> "?"; });
        }
        return sb.toString();
    }
}
