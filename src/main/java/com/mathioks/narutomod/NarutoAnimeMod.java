package com.mathioks.narutomod;

import com.mathioks.narutomod.advancement.ModTriggers;
import com.mathioks.narutomod.jutsu.JutsuTable;
import com.mathioks.narutomod.network.ModNetwork;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Naruto Anime Mod — port para Minecraft 26.3 / Forge 66.
 *
 * <p>Diferença central em relação ao mod original (1.7.10): os itens de
 * ativação de jutsu foram removidos. A ativação agora é feita por combos
 * das teclas C / V / B (estilo Sekwah41), com página de conquistas.
 */
@Mod(NarutoAnimeMod.MODID)
public final class NarutoAnimeMod {
    public static final String MODID = "am";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NarutoAnimeMod(FMLJavaModLoadingContext context) {
        var bus = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(bus).addListener(this::setup);
        ModTriggers.TRIGGERS.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModNetwork.init();
        JutsuTable.init();
        if (net.minecraftforge.fml.loading.FMLEnvironment.dist.isClient()) {
            com.mathioks.narutomod.client.NarutoKeys.init();
        }
        LOGGER.info("[Naruto Anime Mod] {} jutsus ativos no sistema de combos C/V/B", JutsuTable.todos().size());
    }
}
