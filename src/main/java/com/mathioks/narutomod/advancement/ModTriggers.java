package com.mathioks.narutomod.advancement;

import com.mathioks.narutomod.NarutoAnimeMod;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, NarutoAnimeMod.MODID);

    public static final RegistryObject<CastJutsuTrigger> CAST_JUTSU =
            TRIGGERS.register("cast_jutsu", CastJutsuTrigger::new);

    private ModTriggers() {}
}
