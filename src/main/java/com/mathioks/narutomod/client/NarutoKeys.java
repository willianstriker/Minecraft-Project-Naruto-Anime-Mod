package com.mathioks.narutomod.client;

import com.mathioks.narutomod.NarutoAnimeMod;
import com.mathioks.narutomod.jutsu.ComboEngine;
import com.mathioks.narutomod.jutsu.JutsuTable;
import com.mathioks.narutomod.network.CastJutsuPacket;
import com.mathioks.narutomod.network.ModNetwork;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;

/**
 * Cliente: teclas de jutsu C / V / B e leitura dos combos.
 * Mesmas teclas do mod de referência (Sekwah41's): C, V, B.
 * No 26.3 os códigos de tecla são scancodes (InputConstants.KEY_*), não GLFW.
 *
 * <p><b>IMPORTANTE (Minecraft 26.3):</b> o cliente termina de iniciar ANTES do
 * {@code FMLCommonSetupEvent}. Se os listeners forem registrados no setup, o evento
 * {@code RegisterKeyMappingsEvent} já passou e as teclas C/V/B não existem no jogo
 * (bug corrigido na v0.1.1). Por isso {@link #registrarListeners()} é chamado na
 * construção do mod.
 */
public final class NarutoKeys {

    private static KeyMapping.Category CATEGORIA;

    public static KeyMapping TECLA_C, TECLA_V, TECLA_B, TECLA_MENU;

    private static final ComboEngine ENGINE = new ComboEngine();
    private static boolean pronto = false;
    private static boolean avisoMostrado = false;
    private static boolean toastMostrado = false;

    public static ComboEngine engine() { return ENGINE; }

    private static KeyMapping.Category categoria() {
        if (CATEGORIA == null) {
            CATEGORIA = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("am", "jutsus"));
        }
        return CATEGORIA;
    }

    /** Chamado na construção do mod (fase mais cedo possível do lado cliente). */
    public static void registrarListeners() {
        if (pronto) return;
        pronto = true;
        JutsuTable.init();
        for (JutsuTable.Jutsu j : JutsuTable.todos()) {
            ENGINE.registrar(new ComboEngine.JutsuDef(j.id(), j.combo(), j.nome(), j.nome(),
                    j.rank(), "", j.carga(), ""));
        }
        RegisterKeyMappingsEvent.BUS.addListener(NarutoKeys::registrarTeclas);
        TickEvent.ClientTickEvent.Post.BUS.addListener(NarutoKeys::tick);
        // O tick do cliente no 26.3 so roda com mundo aberto — para provar o carregamento
        // ja NO MENU, usamos o evento de tela (Init.Post).
        ScreenEvent.Init.Post.BUS.addListener(NarutoKeys::aoAbrirTela);
        ScreenEvent.Render.Post.BUS.addListener(NarutoKeys::aoRenderizarTela);
        NarutoAnimeMod.LOGGER.info(
                "[Naruto Anime Mod] listeners do cliente registrados na construcao do mod (teclas C/V/B/J)");
    }

    private static void registrarTeclas(RegisterKeyMappingsEvent event) {
        TECLA_C = new KeyMapping("key.am.jutsu_c", InputConstants.KEY_C, categoria());
        TECLA_V = new KeyMapping("key.am.jutsu_v", InputConstants.KEY_V, categoria());
        TECLA_B = new KeyMapping("key.am.jutsu_b", InputConstants.KEY_B, categoria());
        TECLA_MENU = new KeyMapping("key.am.jutsu_menu", InputConstants.KEY_J, categoria());   // J
        event.register(TECLA_C);
        event.register(TECLA_V);
        event.register(TECLA_B);
        event.register(TECLA_MENU);
        NarutoAnimeMod.LOGGER.info(
                "[Naruto Anime Mod] 4 teclas registradas: C, V, B (jutsus) e J (menu)");
    }

    /** Aviso + titulo da janela, disparado quando uma tela e aberta (ex.: menu principal). */
    private static void aoAbrirTela(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof net.minecraft.client.gui.screens.TitleScreen && !toastMostrado) {
            toastMostrado = true;
            avisarCarregado(Minecraft.getInstance());
        }
        // Na tela "Mods" (onde o jogador confere se o mod está instalado) mostramos o aviso de novo.
        if (event.getScreen() instanceof net.minecraftforge.client.gui.ModListScreen) {
            avisarCarregado(Minecraft.getInstance());
        }
    }

    /**
     * Banner fixo desenhado por cima da tela — prova de carregamento que NÃO desaparece
     * (diferente do toast e do título da janela, que o próprio Minecraft sobrescreve).
     */
    private static void aoRenderizarTela(ScreenEvent.Render.Post event) {
        String texto = null;
        if (event.getScreen() instanceof net.minecraft.client.gui.screens.TitleScreen) {
            texto = "Naruto Anime Mod v0.1.2 CARREGADO - " + JutsuTable.todos().size()
                    + " jutsus ativos (combos C/V/B)";
        } else if (event.getScreen() instanceof net.minecraftforge.client.gui.ModListScreen) {
            texto = "Naruto Anime Mod (port 26.3) 0.1.2 - carregado! " + JutsuTable.todos().size()
                    + " jutsus ativos (combos C/V/B)";
        }
        if (texto == null) return;
        try {
            Minecraft mc = Minecraft.getInstance();
            var gg = event.getGuiGraphics();
            int largura = mc.font.width(texto);
            int x = 6, y = 6;
            gg.fill(x - 3, y - 3, x + largura + 3, y + 12, 0xC0101008);
            gg.text(mc.font, texto, x, y + 1, 0xFFFFD700);
        } catch (Throwable t) {
            // desenho nunca deve quebrar o jogo
        }
    }

    /** Prova visual de que o mod carregou: titulo da janela + toast na tela. */
    private static void avisarCarregado(Minecraft mc) {
        try {
            mc.gui.toastManager().addToast(new SystemToast(
                    SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                    Component.literal("Naruto Anime Mod v0.1.2 - CARREGADO"),
                    Component.literal(JutsuTable.todos().size()
                            + " jutsus ativos - combos C/V/B (tecla L: conquistas)")));
        } catch (Throwable t) {
            NarutoAnimeMod.LOGGER.warn("[Naruto Anime Mod] nao consegui exibir o toast: {}", t.toString());
        }
    }

    private static void anunciarNaJanela(Minecraft mc) {
        avisarCarregado(mc);
    }

    private static void tick(TickEvent.ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        // Prova visual dupla de que o mod carregou (sem entrar em mundo, sem tecla, sem log):
        //   1) titulo da janela do jogo;
        //   2) aviso (toast) no canto da tela.
        if (!toastMostrado) {
            toastMostrado = true;
            try {
                mc.gui.toastManager().addToast(new SystemToast(
                        SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                        Component.literal("Naruto Anime Mod v0.1.2 - CARREGADO"),
                        Component.literal(JutsuTable.todos().size()
                                + " jutsus ativos - combos C/V/B (tecla L: conquistas)")));
            } catch (Throwable t) {
                NarutoAnimeMod.LOGGER.warn("[Naruto Anime Mod] nao consegui exibir o toast: {}", t.toString());
            }
            anunciarNaJanela(mc);
        }

        if (mc.player == null) {
            avisoMostrado = false;
            return;
        }

        // Aviso de boas-vindas: tambem serve como prova visual de que o mod carregou.
        if (!avisoMostrado) {
            avisoMostrado = true;
            mc.player.sendSystemMessage(Component.literal(
                    "[Naruto Anime Mod] " + JutsuTable.todos().size()
                            + " jutsus ativos - combos C/V/B. A aba de conquistas (tecla L) mostra as combinações.")
                    .withStyle(ChatFormatting.GOLD));
        }

        if (TECLA_C == null) return;

        if (TECLA_C.consumeClick()) ENGINE.pressionar(ComboEngine.KEY_C);
        if (TECLA_V.consumeClick()) ENGINE.pressionar(ComboEngine.KEY_V);
        if (TECLA_B.consumeClick()) ENGINE.pressionar(ComboEngine.KEY_B);

        boolean segurando = TECLA_C.isDown() || TECLA_V.isDown() || TECLA_B.isDown();
        ENGINE.tickSegurando(segurando);
        ENGINE.tick();

        for (ComboEngine.Cast cast : ENGINE.drenarCasts()) {
            ModNetwork.toServer(new CastJutsuPacket(cast.jutsu().combo(), cast.ticksCarregados()));
        }
        for (ComboEngine.Falha falha : ENGINE.drenarFalhas()) {
            mc.player.sendSystemMessage(
                    Component.literal("Nenhum jutsu para: " + ComboEngine.teclas(falha.combo()))
                            .withStyle(ChatFormatting.RED));
        }
    }

    public static String bufferHud() {
        String b = ENGINE.bufferAtual();
        return b.isEmpty() ? "" : ComboEngine.teclas(b);
    }

    private NarutoKeys() {}
}
