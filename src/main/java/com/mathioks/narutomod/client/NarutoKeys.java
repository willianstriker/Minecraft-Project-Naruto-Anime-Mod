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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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

    private static void tick(TickEvent.ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
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
