package com.mathioks.narutomod;

import net.minecraftforge.common.ForgeConfigSpec;

public final class Config {
    private static final ForgeConfigSpec.Builder B = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue CAST_DELAY;
    public static final ForgeConfigSpec.IntValue CHAKRA_MAXIMO;
    public static final ForgeConfigSpec.DoubleValue CHAKRA_REGEN;
    public static final ForgeConfigSpec.BooleanValue CHAKRA_ATIVO;

    static {
        B.comment("Sistema de ativacao de jutsus por teclas C/V/B").push("jutsus");
        CAST_DELAY = B.comment("Ticks de espera apos a ultima tecla para o jutsu disparar")
                .defineInRange("combo_cast_delay", 4, 1, 40);
        B.pop();
        B.comment("Chakra").push("chakra");
        CHAKRA_ATIVO = B.define("chakra_ativo", true);
        CHAKRA_MAXIMO = B.defineInRange("chakra_maximo", 2000, 100, 100000);
        CHAKRA_REGEN = B.comment("Chakra regenerado por tick")
                .defineInRange("chakra_regen_por_tick", 0.5D, 0.0D, 100.0D);
        B.pop();
        SPEC = B.build();
    }

    public static final ForgeConfigSpec SPEC;
    private Config() {}
}
