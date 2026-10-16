package com.mathioks.narutomod.jutsu;

import com.mathioks.narutomod.advancement.ModTriggers;
import com.mathioks.narutomod.chakra.ChakraData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/** Executa o efeito de cada jutsu no servidor. */
public final class JutsuCaster {

    public static void cast(ServerPlayer p, String combo, int ticksCarregados) {
        Optional<JutsuTable.Jutsu> opt = JutsuTable.porCombo(combo);
        if (opt.isEmpty()) {
            p.sendSystemMessage(Component.literal("Combo inexistente: " + ComboEngine.teclas(combo))
                    .withStyle(net.minecraft.ChatFormatting.RED));
            return;
        }
        JutsuTable.Jutsu j = opt.get();
        int custo = j.custo();
        if (!ChakraData.gastar(p, custo)) {
            p.sendSystemMessage(Component.literal("Chakra insuficiente para " + j.nome())
                    .withStyle(net.minecraft.ChatFormatting.RED));
            return;
        }
        float poder = j.carga() ? Math.min(1f, 0.25f + ticksCarregados / 40f) : 1f;
        executar(p, j, poder);
        ModTriggers.CAST_JUTSU.get().trigger(p, j.id());
        p.sendSystemMessage(Component.literal("» " + j.nome() + (j.carga() ? String.format(" (carga %.0f%%)", poder * 100) : ""))
                .withStyle(net.minecraft.ChatFormatting.AQUA));
    }

    private static void executar(ServerPlayer p, JutsuTable.Jutsu j, float poder) {
        ServerLevel nivel = p.level();
        Vec3 olhar = p.getLookAngle();
        switch (j.id()) {
            case "kawarimi" -> {
                p.teleportTo(p.getX() - olhar.x * 4, p.getY(), p.getZ() - olhar.z * 4);
                particulas(nivel, p, ParticleTypes.CLOUD, 20);
            }
            case "shunshin" -> {
                p.push(olhar.x * 2.2, 0.25, olhar.z * 2.2);
                particulas(nivel, p, ParticleTypes.CLOUD, 12);
            }
            case "henge" -> {
                p.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 0));
                particulas(nivel, p, ParticleTypes.SMOKE, 24);
            }
            case "kage_bunshin", "taju_kage_bunshin" -> {
                p.addEffect(new MobEffectInstance(MobEffects.SPEED, 200, 1));
                particulas(nivel, p, ParticleTypes.CLOUD, j.id().equals("taju_kage_bunshin") ? 80 : 30);
            }
            case "goukakyuu", "rasenshuriken" -> {
                LargeFireball bola = new LargeFireball(nivel, p, olhar, (int) (2 * poder));
                bola.setPos(p.getX() + olhar.x, p.getEyeY(), p.getZ() + olhar.z);
                nivel.addFreshEntity(bola);
            }
            case "housenka", "suidan", "daitoppa" -> {
                for (int i = -1; i <= 1; i++) particulas(nivel, p, ParticleTypes.FLAME, 6);
                dano(p, 6 * poder, 6, 0.4);
            }
            case "amaterasu" -> {
                dano(p, 10 * poder, 8, 0.3);
                nivel.playSound(null, p.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 0.6f);
            }
            case "rasengan", "chidori", "raikiri" -> {
                dano(p, 14 * poder, 4, 9 * poder);
                particulas(nivel, p, j.id().equals("rasengan") ? ParticleTypes.CLOUD : ParticleTypes.ELECTRIC_SPARK, 30);
                nivel.playSound(null, p.blockPosition(),
                        j.id().equals("rasengan") ? SoundEvents.PLAYER_ATTACK_SWEEP : SoundEvents.LIGHTNING_BOLT_THUNDER,
                        SoundSource.PLAYERS, 1f, 1.2f);
            }
            case "kirin" -> {
                dano(p, 30 * poder, 24, 4);
                particulas(nivel, p, ParticleTypes.ELECTRIC_SPARK, 120);
            }
            case "suirou" -> {
                danoAlvoMaisProximo(p, 10, 6, 0.2, MobEffects.SLOWNESS, 100);
            }
            case "bakusui_shouha", "shinra_tensei", "bansho_tenin" -> {
                double raio = 10;
                List<Entity> alvos = nivel.getEntities(p, p.getBoundingBox().inflate(raio));
                boolean puxar = j.id().equals("bansho_tenin");
                for (Entity e : alvos) {
                    if (!(e instanceof LivingEntity le) || e == p) continue;
                    Vec3 direcao = puxar ? e.position().subtract(p.position()).normalize().scale(-1.5)
                                         : e.position().subtract(p.position()).normalize().scale(2.5);
                    le.push(direcao.x, 0.4, direcao.z);
                    if (!puxar) le.hurt(p.damageSources().playerAttack(p), 6f * poder);
                }
                particulas(nivel, p, ParticleTypes.EXPLOSION, 30);
            }
            case "goukakyuu_x" -> {}
            case "jukai_koutan", "doryuusou", "doryuu_jouheki" -> {
                particulas(nivel, p, ParticleTypes.HAPPY_VILLAGER, 40);
                dano(p, 5 * poder, 5, 0.5);
            }
            case "makyou_hyoushou" -> particulas(nivel, p, ParticleTypes.SNOWFLAKE, 60);
            case "kamui" -> {
                p.teleportTo(p.getX() + olhar.x * 12, p.getY(), p.getZ() + olhar.z * 12);
                particulas(nivel, p, ParticleTypes.PORTAL, 50);
            }
            case "sage_mode" -> {
                p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
                p.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 600, 1));
                p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 600, 0));
            }
            case "bijuu_chakra_mode" -> {
                p.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 900, 2));
                p.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 900, 0));
                p.addEffect(new MobEffectInstance(MobEffects.SPEED, 900, 1));
            }
            case "curse_mark_2" -> {
                p.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 600, 1));
                p.addEffect(new MobEffectInstance(MobEffects.SPEED, 600, 1));
            }
            case "byakugan" -> {
                p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0));
                for (Entity e : nivel.getEntities(p, p.getBoundingBox().inflate(24))) {
                    if (e instanceof LivingEntity le && e != p)
                        le.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
                }
            }
            case "sharingan" -> {
                p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0));
                p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0));
            }
            case "eight_gates" -> {
                p.addEffect(new MobEffectInstance(MobEffects.SPEED, 400, 3));
                p.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 400, 2));
            }
            case "healing_jutsu" -> p.heal(8f * poder);
            case "creation_rebirth" -> {
                p.heal(20f);
                p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 2));
            }
            case "edo_tensei" -> {
                p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 400, 1));
                particulas(nivel, p, ParticleTypes.SOUL, 40);
            }
            case "goukakyuu_miss" -> {}
            default -> particulas(nivel, p, ParticleTypes.CRIT, 15);
        }
    }

    // ------------------------------------------------------------------ helpers
    private static void particulas(ServerLevel nivel, ServerPlayer p, net.minecraft.core.particles.SimpleParticleType tipo, int qtd) {
        nivel.sendParticles(tipo, p.getX(), p.getEyeY(), p.getZ(), qtd, 0.5, 0.5, 0.5, 0.02);
    }

    private static void dano(ServerPlayer p, float dano, double alcance, double empurrao) {
        AABB area = p.getBoundingBox().inflate(alcance).move(p.getLookAngle().scale(alcance / 2.0));
        for (Entity e : p.level().getEntities(p, area)) {
            if (e instanceof LivingEntity le && e != p) {
                le.hurt(p.damageSources().playerAttack(p), dano);
                Vec3 dir = le.position().subtract(p.position()).normalize().scale(empurrao);
                le.push(dir.x, Math.max(0.2, empurrao * 0.1), dir.z);
            }
        }
    }

    private static void danoAlvoMaisProximo(ServerPlayer p, float dano, double alcance, double empurrao,
                                            net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> efeito, int dur) {
        dano(p, dano, alcance, empurrao);
        for (Entity e : p.level().getEntities(p, p.getBoundingBox().inflate(alcance))) {
            if (e instanceof LivingEntity le && e != p)
                le.addEffect(new MobEffectInstance(efeito, dur, 1));
        }
    }

    private JutsuCaster() {}
}
