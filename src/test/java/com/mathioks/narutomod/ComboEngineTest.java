package com.mathioks.narutomod;

import com.mathioks.narutomod.jutsu.ComboEngine;
import com.mathioks.narutomod.jutsu.ComboEngine.Cast;
import com.mathioks.narutomod.jutsu.ComboEngine.JutsuDef;
import com.mathioks.narutomod.jutsu.ComboEngine.Falha;

import java.util.List;

/** Teste de console do motor de combos (roda sem Gradle/Minecraft). */
public class ComboEngineTest {

    private static int falhas = 0, total = 0;
    static void check(String nome, boolean cond) {
        total++;
        System.out.printf("  %s %s%n", cond ? "[OK]   " : "[FALHA]", nome);
        if (!cond) falhas++;
    }
    /** avança N ticks sem tecla segurada */
    static void ticks(ComboEngine e, int n) { for (int i = 0; i < n; i++) { e.tickSegurando(false); e.tick(); } }

    public static void main(String[] args) {
        ComboEngine e = new ComboEngine();
        e.registrar(new JutsuDef("kawarimi", "1", "Substitution Jutsu", "Kawarimi", "E", "Basico", false, ""));
        e.registrar(new JutsuDef("shunshin", "3", "Body Flicker", "Shunshin", "D", "Basico", false, ""));
        e.registrar(new JutsuDef("kage_bunshin", "12", "Kage Bunshin", "Kage Bunshin", "B", "Basico", false, ""));
        e.registrar(new JutsuDef("rasengan", "122", "Rasengan", "Rasengan", "A", "Vento", true, ""));
        e.registrar(new JutsuDef("daitoppa", "121", "Great Breakthrough", "Daitoppa", "C", "Vento", false, ""));
        e.registrar(new JutsuDef("chidori", "211", "Chidori", "Chidori", "A", "Raio", true, ""));

        System.out.println("== 1. combo de 1 tecla (commit por atraso) ==");
        e.pressionar(ComboEngine.KEY_C);
        ticks(e, ComboEngine.CAST_DELAY_TICKS);           // ainda dentro do atraso
        check("C nao dispara antes do atraso", e.drenarCasts().isEmpty());
        ticks(e, 2);                                       // atraso estourado
        List<Cast> c1 = e.drenarCasts();
        check("C dispara Kawarimi apos o atraso", c1.size() == 1 && c1.get(0).jutsu().id().equals("kawarimi"));

        System.out.println("== 2. combo de 2 teclas em sequencia rapida ==");
        e.pressionar(1); e.pressionar(2);
        ticks(e, ComboEngine.CAST_DELAY_TICKS + 2);
        List<Cast> c2 = e.drenarCasts();
        check("C-V dispara Kage Bunshin", c2.size() == 1 && c2.get(0).jutsu().id().equals("kage_bunshin"));

        System.out.println("== 3. combo de 3 teclas ==");
        e.pressionar(1); e.pressionar(2); e.pressionar(1);
        ticks(e, ComboEngine.CAST_DELAY_TICKS + 2);
        List<Cast> c3 = e.drenarCasts();
        check("C-V-C dispara Daitoppa", c3.size() == 1 && c3.get(0).jutsu().id().equals("daitoppa")
                && !c3.get(0).carregado());

        System.out.println("== 4. jutsu CARREGADO: segurar a ultima tecla ==");
        e.pressionar(1); e.pressionar(2); e.pressionar(2);
        for (int i = 0; i < ComboEngine.HOLD_THRESHOLD_TICKS + 1; i++) { e.tickSegurando(true); e.tick(); }
        check("segurar C-V-V inicia a carga do Rasengan", e.isCarregando());
        check("nada foi disparado antes de soltar", e.drenarCasts().isEmpty());
        // segura mais um pouco e solta
        for (int i = 0; i < 20; i++) { e.tickSegurando(true); e.tick(); }
        e.tickSegurando(false);
        e.soltar();
        List<Cast> c4 = e.drenarCasts();
        check("soltar libera o Rasengan carregado",
                c4.size() == 1 && c4.get(0).jutsu().id().equals("rasengan")
                        && c4.get(0).carregado() && c4.get(0).ticksCarregados() >= 20);

        System.out.println("== 5. toque rapido em jutsu de carga = versao fraca ==");
        e.pressionar(2); e.pressionar(1); e.pressionar(1);
        e.tickSegurando(false);
        e.soltar();
        List<Cast> c5 = e.drenarCasts();
        check("V-C-C com toque rapido libera Chidori em carga minima (fraco)",
                c5.size() == 1 && c5.get(0).jutsu().id().equals("chidori")
                        && c5.get(0).ticksCarregados() < ComboEngine.HOLD_THRESHOLD_TICKS);

        System.out.println("== 6. combo inexistente ==");
        e.pressionar(3); e.pressionar(3); e.pressionar(3);
        ticks(e, ComboEngine.CAST_DELAY_TICKS + 2);
        List<Falha> f = e.drenarFalhas();
        check("B-B-B gera falha 'combo inexistente'", f.size() == 1 && f.get(0).combo().equals("333"));
        check("nenhum jutsu disparado no combo invalido", e.drenarCasts().isEmpty());

        System.out.println("== 7. buffer e reset ==");
        e.pressionar(1);
        check("buffer guarda a tecla", e.bufferAtual().equals("1"));
        e.reset();
        check("reset limpa o buffer", e.bufferAtual().isEmpty());

        System.out.println("== 8. traducao de teclas e prefixos ==");
        check("122 -> C-V-V", ComboEngine.teclas("122").equals("C-V-V"));
        check("C e prefixo de combo maior", e.temPrefixo("1"));
        check("C-V-C nao tem filhos", !e.temPrefixo("121"));

        System.out.printf("%nRESULTADO: %d/%d testes passaram%n", total - falhas, total);
        if (falhas > 0) System.exit(1);
    }
}
