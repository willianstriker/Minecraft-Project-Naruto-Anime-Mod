package com.sekwah.narutomod.client.keybinds;

import com.mojang.logging.LogUtils;
import com.sekwah.narutomod.abilities.Ability;
import com.sekwah.narutomod.abilities.NarutoAbilities;
import com.sekwah.narutomod.abilities.Ability.ActivationType;
import com.sekwah.narutomod.capabilities.NinjaCapabilityHandler;
import com.sekwah.narutomod.client.gui.JutsuScreen;
import com.sekwah.narutomod.config.NarutoConfig;
import com.sekwah.narutomod.network.PacketHandler;
import com.sekwah.narutomod.network.c2s.ServerJutsuCastingPacket;
import com.sekwah.narutomod.network.c2s.ServerAbilityChannelPacket.ChannelStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.slf4j.Logger;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "narutomod",
   bus = Bus.FORGE
)
public class NarutoKeyHandler {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Map<String, KeyBindingTickHeld> keys = new HashMap<>();
   private static final String NARUTO_KEY_CATEGORY = "narutomod.keys.category";
   private static final List<KeyBindingTickHeld> JUTSU_KEYS = new ArrayList<>();
   private static long currentJutsuCombo = 0L;
   private static Ability currentJutsuComboAbility = null;
   private static final long MAX_JUTSU_VALUE = 9999999999L;
   private static int ticksSinceLastKey = 0;
   private static boolean isCurrentlyChargingAbility = false;
   private static KeyBindingTickHeld LEAP_KEY;
   private static KeyBindingTickHeld JUTSU_C_KEY;
   private static KeyBindingTickHeld JUTSU_V_KEY;
   private static KeyBindingTickHeld JUTSU_B_KEY;
   private static KeyBindingTickHeld JUTSU_MENU_KEY;

   @SubscribeEvent
   public static void registerKeyBinds(RegisterKeyMappingsEvent event) {
      LEAP_KEY = registerKeyBind("naruto.keys.leap", 88, event);
      JUTSU_C_KEY = registerKeyBind("naruto.keys.key1", 67, event);
      JUTSU_V_KEY = registerKeyBind("naruto.keys.key2", 86, event);
      JUTSU_B_KEY = registerKeyBind("naruto.keys.key3", 66, event);
      JUTSU_MENU_KEY = registerKeyBind("naruto.keys.jutsu_menu", 74, event);
      JUTSU_KEYS.add(LEAP_KEY);
      JUTSU_KEYS.add(JUTSU_C_KEY);
      JUTSU_KEYS.add(JUTSU_V_KEY);
      JUTSU_KEYS.add(JUTSU_B_KEY);
      JUTSU_KEYS.add(JUTSU_MENU_KEY);
      LEAP_KEY.registerClickConsumer(() -> {
         Minecraft mc = Minecraft.m_91087_();
         if (mc.f_91074_ != null) {
            NarutoAbilities.triggerAbility(NarutoAbilities.LEAP.getId());
         }
      });
      JUTSU_MENU_KEY.registerClickConsumer(() -> {
         Minecraft mc = Minecraft.m_91087_();
         if (mc.f_91074_ != null) {
            mc.m_91152_(new JutsuScreen());
         }
      });
      JUTSU_C_KEY.registerClickConsumer(() -> handleJustuKey(1));
      JUTSU_V_KEY.registerClickConsumer(() -> handleJustuKey(2));
      JUTSU_B_KEY.registerClickConsumer(() -> handleJustuKey(3));
   }

   public static KeyBindingTickHeld registerKeyBind(String name, int keyCode, RegisterKeyMappingsEvent event) {
      if (keys.containsKey(name)) {
         return keys.get(name);
      } else {
         KeyBindingTickHeld key = new KeyBindingTickHeld(name, keyCode, "narutomod.keys.category");
         keys.put(name, key);
         event.register(key);
         return key;
      }
   }

   public static void handleJustuKey(int i) {
      if (!isCurrentlyChargingAbility) {
         Minecraft mc = Minecraft.m_91087_();
         if (mc.f_91074_ != null) {
            LocalPlayer player = mc.f_91074_;
            player.getCapability(NinjaCapabilityHandler.NINJA_DATA).ifPresent(ninjaData -> {
               if (!ninjaData.isNinjaModeEnabled()) {
                  mc.f_91074_.m_5661_(Component.m_237115_("jutsu.not_a_ninja").m_130940_(ChatFormatting.RED), true);
               } else {
                  PacketHandler.sendToServer(new ServerJutsuCastingPacket(i));
                  ticksSinceLastKey = 0;
                  if (currentJutsuCombo < 9999999999L) {
                     currentJutsuCombo *= 10L;
                     currentJutsuCombo += i;
                     currentJutsuComboAbility = NarutoAbilities.getAbilityFromCombo(currentJutsuCombo);
                  } else {
                     LOGGER.info("Combo too long, ignoring keypress");
                  }
               }
            });
         }
      }
   }

   @SubscribeEvent
   public static void onClientTick(ClientTickEvent event) {
      if (currentJutsuCombo != 0L) {
         ticksSinceLastKey++;
      }

      int lastKey = (int)(currentJutsuCombo % 10L);
      boolean lastKeyHeld = false;
      boolean isPossibleAbilityCharged = currentJutsuComboAbility != null && currentJutsuComboAbility.activationType() == ActivationType.CHANNELED;
      KeyBindingTickHeld currentKey = null;
      if (lastKey > 0 && lastKey < JUTSU_KEYS.size()) {
         currentKey = JUTSU_KEYS.get(lastKey);
         if (currentKey.m_90857_()) {
            lastKeyHeld = true;
         }
      }

      if (isPossibleAbilityCharged) {
         checkCharging(currentKey, lastKeyHeld);
      } else {
         checkNonCharging();
      }

      for (KeyBindingTickHeld key : JUTSU_KEYS) {
         key.update();
      }
   }

   private static void checkCharging(KeyBindingTickHeld currentKey, boolean isHoldingLastKey) {
      if (isHoldingLastKey && currentKey.heldTicks >= NarutoConfig.jutsuKeybindHoldThreshold) {
         if (!isCurrentlyChargingAbility) {
            NarutoAbilities.handleCharging(currentJutsuCombo, ChannelStatus.START);
         }

         isCurrentlyChargingAbility = true;
      } else if (!isHoldingLastKey) {
         currentKey.consumeReleaseDuration();
         if (isCurrentlyChargingAbility) {
            NarutoAbilities.handleCharging(currentJutsuCombo, ChannelStatus.STOP);
            resetJutsuCasting();
         } else if (ticksSinceLastKey > NarutoConfig.jutsuCastDelay) {
            NarutoAbilities.handleCharging(currentJutsuCombo, ChannelStatus.MIN_ACTIVATE);
            resetJutsuCasting();
         }

         isCurrentlyChargingAbility = false;
      }
   }

   private static void resetJutsuCasting() {
      ticksSinceLastKey = 0;
      currentJutsuCombo = 0L;
      currentJutsuComboAbility = null;
      isCurrentlyChargingAbility = false;
   }

   private static void checkNonCharging() {
      if (ticksSinceLastKey > NarutoConfig.jutsuCastDelay) {
         Minecraft mc = Minecraft.m_91087_();
         if (mc.f_91074_ != null) {
            LOGGER.debug("Casting jutsu {}", currentJutsuCombo);
            if (!NarutoAbilities.triggerAbility(currentJutsuCombo)) {
               mc.f_91074_.m_5661_(Component.m_237110_("jutsu.error.notfound", new Object[]{currentJutsuCombo}).m_130940_(ChatFormatting.RED), true);
            }
         }

         resetJutsuCasting();
      }
   }
}
