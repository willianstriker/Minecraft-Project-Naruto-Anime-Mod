package com.mathioks.narutomod.chakra;

import com.mathioks.narutomod.Config;
import net.minecraft.world.entity.player.Player;

/** Chakra do jogador, guardado no NBT persistente da entidade (sem capability). */
public final class ChakraData {
    private static final String TAG = "am_chakra";

    public static int get(Player p) {
        return (int) p.getPersistentData().getFloatOr(TAG, Config.CHAKRA_MAXIMO.get());
    }

    public static void set(Player p, int valor) {
        int max = Config.CHAKRA_MAXIMO.get();
        p.getPersistentData().putFloat(TAG, Math.max(0, Math.min(max, valor)));
    }

    public static boolean gastar(Player p, int custo) {
        if (!Config.CHAKRA_ATIVO.get()) return true;
        int atual = get(p);
        if (atual < custo) return false;
        set(p, atual - custo);
        return true;
    }

    public static void regenerar(Player p) {
        if (!Config.CHAKRA_ATIVO.get()) return;
        set(p, (int) (get(p) + Config.CHAKRA_REGEN.get()));
    }

    public static float percentual(Player p) {
        return (float) get(p) / Config.CHAKRA_MAXIMO.get();
    }

    private ChakraData() {}
}
