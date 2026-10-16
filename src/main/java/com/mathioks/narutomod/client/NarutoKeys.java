package com.mathioks.narutomod.client;

import com.mathioks.narutomod.jutsu.ComboEngine;
import com.mathioks.narutomod.jutsu.JutsuTable;
import com.mathioks.narutomod.network.CastJutsuPacket;
import com.mathioks.narutomod.network.ModNetwork;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;

/**
 * Cliente: teclas de jutsu C / V / B e leitura dos combos.
 * Mesmas teclas do mod de referência (Sekwah41): C=67, V=86, B=66.
 */
public final class NarutoKeys {

    public static final KeyMapping.Category CATEGORIA =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath("am", "jutsus"));

    public static KeyMapping TECLA_C, TECLA_V, TECLA_B, TECLA_MENU;

    private static final ComboEngine ENGINE = new ComboEngine();
    private static boolean pronto = false;

    public static ComboEngine engine() { return ENGINE; }

    public static void init() {
        if (pronto) return;
        pronto = true;
        JutsuTable.init();
        for (JutsuTable.Jutsu j : JutsuTable.todos()) {
            ENGINE.registrar(new ComboEngine.JutsuDef(j.id(), j.combo(), j.nome(), j.nome(),
                    j.rank(), "", j.carga(), ""));
        }
        RegisterKeyMappingsEvent.BUS.addListener(NarutoKeys::registrarTeclas);
        TickEvent.ClientTickEvent.Post.BUS.addListener(NarutoKeys::tick);
    }

    private static void registrarTeclas(RegisterKeyMappingsEvent event) {
        TECLA_C = new KeyMapping("key.am.jutsu_c", 67, CATEGORIA);
        TECLA_V = new KeyMapping("key.am.jutsu_v", 86, CATEGORIA);
        TECLA_B = new KeyMapping("key.am.jutsu_b", 66, CATEGORIA);
        TECLA_MENU = new KeyMapping("key.am.jutsu_menu", 74, CATEGORIA);   // J
        event.register(TECLA_C);
        event.register(TECLA_V);
        event.register(TECLA_B);
        event.register(TECLA_MENU);
    }

    private static void tick(TickEvent.ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || TECLA_C == null) return;

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
