package com.mathioks.narutomod.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

/** Gatilho de conquista "am:cast_jutsu" — dispara quando o jogador usa um jutsu. */
public class CastJutsuTrigger extends SimpleCriterionTrigger<CastJutsuTrigger.Instance> {

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, String jutsuId) {
        this.trigger(player, inst -> inst.matches(jutsuId));
    }

    public record Instance(String jutsu) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.STRING.fieldOf("jutsu").forGetter(Instance::jutsu)
        ).apply(i, Instance::new));

        @Override
        public Optional<Holder<LootItemCondition>> player() {
            return Optional.empty();
        }

        public boolean matches(String id) {
            return jutsu.equals(id);
        }
    }
}
