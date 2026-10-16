package alcoholmod.Mathioks;

import alcoholmod.Mathioks.ExtraFunctions.GuiTest;
import alcoholmod.Mathioks.ExtraFunctions.SyncBijuuBombMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncBijuuFormActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncBijuuScaleMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncBijuuSlashMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncByakuganActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncChakraControlMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncChakraMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncCurseSealActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncCurseSealMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu1Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu2Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu3Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu4Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu5Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKenjutsu6Message;
import alcoholmod.Mathioks.ExtraFunctions.SyncKetsuryuganActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSageActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSenChakraMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSharinganActiveMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSharinganMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSixPathsMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSubstitutionMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSusanoAMessage;
import alcoholmod.Mathioks.ExtraFunctions.SyncSusanoMessage;
import alcoholmod.Mathioks.ExtraFunctions.SynctrackAllMessage;
import alcoholmod.Mathioks.FinalUtilities.SubstitutionUtil;
import alcoholmod.Mathioks.block.Blockss;
import alcoholmod.Mathioks.proxys.ClientProxy;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
   int counter;
   int counterEye;
   public World world;
   private int ChakraRestoreCounter = 0;
   private int SubstitutionCounter = 0;
   private SubstitutionUtil SubstitutionUtil;
   private boolean Jumped = false;
   private boolean pressed2;
   private final Minecraft mc;
   private int KenjutsuCounter = 0;

   public KeyHandler() {
      this.mc = Minecraft.func_71410_x();
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL,
      receiveCanceled = true
   )
   public void onEvent(KeyInputEvent event) {
      KeyBinding[] keyBindings = ClientProxy.keyBindings;
      if (keyBindings[0].func_151468_f()) {
      }
   }

   @SubscribeEvent
   public void onPlayerEvent(PlayerTickEvent event) {
      EntityPlayer player = event.player;
      ExtendedPlayer props = ExtendedPlayer.get(player);
      KeyBinding[] keyBindings = ClientProxy.keyBindings;
      if (props.getChakraControl() && this.mc.field_71415_G) {
         if (Keyboard.isKeyDown(57)
            && !player.field_70122_E
            && player.field_70181_x < 0.07 + (ExtendedPlayer.get(this.mc.field_71439_g).getSpeed() * 2.0 - 0.2)
            && !this.Jumped
            && !player.field_71075_bZ.field_75098_d) {
            player.field_70181_x = player.field_70181_x + (0.5 + (ExtendedPlayer.get(this.mc.field_71439_g).getSpeed() * 2.0 - 0.2));
            this.Jumped = true;
         }

         if (player.field_70122_E && this.Jumped) {
            this.Jumped = false;
         }
      }

      if (keyBindings[1].func_151468_f() && this.mc.field_71415_G) {
         if (props.getBijuuScale() < 2) {
            if (props.getsharinganicon() == 1 && props.getSharingan() >= 1) {
               if (props.getSharinganSize() == 0.0) {
                  if (!props.getSharinganActive()) {
                     props.setSharinganActive(true);
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Sharingan!"));
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                  } else if (props.getSharinganActive()) {
                     props.setSharinganActive(false);
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                     if (props.getSusanoA()) {
                        props.setSusanoA(false);
                        PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     }
                  }
               }

               if (props.getSharinganSize() == 1.0) {
                  if (Keyboard.isKeyDown(42)) {
                     if (props.getSharingan() == 3) {
                        if (props.getmangekyouSharingan() == 1) {
                           props.setSharingan(48);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 2) {
                           props.setSharingan(35);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 3) {
                           props.setSharingan(4);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 4) {
                           props.setSharingan(7);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 5) {
                           props.setSharingan(10);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 6) {
                           props.setSharingan(13);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 7) {
                           props.setSharingan(18);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 8) {
                           props.setSharingan(24);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 9) {
                           props.setSharingan(20);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 10) {
                           props.setSharingan(16);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 11) {
                           props.setSharingan(26);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 12) {
                           props.setSharingan(28);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 13) {
                           props.setSharingan(30);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 14) {
                           props.setSharingan(0);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 15) {
                           props.setSharingan(22);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 16) {
                           props.setSharingan(32);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 17) {
                           props.setSharingan(35);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Mangekyou Sharingan!"));
                     } else if (props.getSharingan() >= 4) {
                        props.setSharingan(3);
                        PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                     }
                  } else if (!props.getSharinganActive()) {
                     props.setSharinganActive(true);
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Sharingan!"));
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                  } else if (props.getSharinganActive()) {
                     props.setSharinganActive(false);
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                     if (props.getSusanoA()) {
                        props.setSusanoA(false);
                        PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     }
                  }
               }

               if (props.getSharinganSize() == 2.0) {
                  if (Keyboard.isKeyDown(42)) {
                     if (props.getSharingan() == 3) {
                        if (props.getmangekyouSharingan() == 1) {
                           props.setSharingan(49);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 2) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 3) {
                           props.setSharingan(5);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 4) {
                           props.setSharingan(8);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 5) {
                           props.setSharingan(11);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 6) {
                           props.setSharingan(14);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 7) {
                           props.setSharingan(19);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 8) {
                           props.setSharingan(25);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 9) {
                           props.setSharingan(21);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 10) {
                           props.setSharingan(17);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 11) {
                           props.setSharingan(27);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 12) {
                           props.setSharingan(29);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 13) {
                           props.setSharingan(31);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 14) {
                           props.setSharingan(0);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 15) {
                           props.setSharingan(23);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 16) {
                           props.setSharingan(33);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 17) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Eternal Mangekyou Sharingan!"));
                     } else if (props.getSharingan() >= 5) {
                        props.setSharingan(3);
                        PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                     }
                  } else if (!props.getSharinganActive()) {
                     props.setSharinganActive(true);
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Sharingan!"));
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                  } else if (props.getSharinganActive()) {
                     props.setSharinganActive(false);
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                     if (props.getSusanoA()) {
                        props.setSusanoA(false);
                        PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     }
                  }
               }

               if (props.getSharinganSize() == 3.0) {
                  if (Keyboard.isKeyDown(42)) {
                     if (props.getSharingan() == 3) {
                        if (props.getmangekyouSharingan() == 1) {
                           props.setSharingan(49);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 2) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 3) {
                           props.setSharingan(5);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 4) {
                           props.setSharingan(8);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 5) {
                           props.setSharingan(11);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 6) {
                           props.setSharingan(14);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 7) {
                           props.setSharingan(19);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 8) {
                           props.setSharingan(25);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 9) {
                           props.setSharingan(21);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 10) {
                           props.setSharingan(17);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 11) {
                           props.setSharingan(27);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 12) {
                           props.setSharingan(29);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 13) {
                           props.setSharingan(31);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 14) {
                           props.setSharingan(0);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 15) {
                           props.setSharingan(23);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 16) {
                           props.setSharingan(33);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 17) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Eternal Mangekyou Sharingan!"));
                     } else if (props.getSharingan() >= 5) {
                        props.setSharingan(3);
                        PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                     }
                  } else if (Keyboard.isKeyDown(29)) {
                     if (props.getSharingan() == 50
                        || props.getSharingan() == 37
                        || props.getSharingan() == 6
                        || props.getSharingan() == 9
                        || props.getSharingan() == 12
                        || props.getSharingan() == 15
                        || props.getSharingan() == 41
                        || props.getSharingan() == 44
                        || props.getSharingan() == 42
                        || props.getSharingan() == 40
                        || props.getSharingan() == 45
                        || props.getSharingan() == 46
                        || props.getSharingan() == 47
                        || props.getSharingan() == 43
                        || props.getSharingan() == 34) {
                        if (props.getmangekyouSharingan() == 1) {
                           props.setSharingan(49);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 2) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 3) {
                           props.setSharingan(5);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 4) {
                           props.setSharingan(8);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 5) {
                           props.setSharingan(11);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 6) {
                           props.setSharingan(14);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 7) {
                           props.setSharingan(19);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 8) {
                           props.setSharingan(25);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 9) {
                           props.setSharingan(21);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 10) {
                           props.setSharingan(17);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 11) {
                           props.setSharingan(27);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 12) {
                           props.setSharingan(29);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 13) {
                           props.setSharingan(31);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 14) {
                           props.setSharingan(0);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 15) {
                           props.setSharingan(23);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 16) {
                           props.setSharingan(33);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        if (props.getmangekyouSharingan() == 17) {
                           props.setSharingan(36);
                           PacketDispatcher.sendToServer(new SyncSharinganMessage(player));
                        }

                        player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Eternal Mangekyou Sharingan!"));
                     }
                  } else if (!props.getSharinganActive()) {
                     props.setSharinganActive(true);
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Sharingan!"));
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                  } else if (props.getSharinganActive()) {
                     props.setSharinganActive(false);
                     PacketDispatcher.sendToServer(new SyncSharinganActiveMessage(player));
                     if (props.getSusanoA()) {
                        props.setSusanoA(false);
                        PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     }
                  }
               }
            }
         } else {
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "You are in Tailed beast Mode!"));
         }
      }

      if (keyBindings[5].func_151468_f()) {
         int blockX = MathHelper.func_76128_c(player.field_70165_t);
         int blockY = MathHelper.func_76128_c(player.field_70163_u - 0.2 - player.field_70129_M);
         int blockZ = MathHelper.func_76128_c(player.field_70161_v);
         if (player.field_70170_p.func_147439_a(blockX, blockY, blockZ) != Blockss.bloodPrisonBlock && this.mc.field_71415_G && this.SubstitutionCounter == 0) {
            PacketDispatcher.sendToServer(new SyncSubstitutionMessage(true, player));
            this.SubstitutionCounter = 2000;
         }
      }

      if (this.KenjutsuCounter > 0) {
         this.KenjutsuCounter--;
      }

      if (this.SubstitutionCounter > 0) {
         this.SubstitutionCounter--;
      }

      if (keyBindings[10].func_151468_f() && this.mc.field_71415_G) {
         PacketDispatcher.sendToServer(new SyncBijuuBombMessage(true, player));
      }

      if (keyBindings[2].func_151468_f() && this.mc.field_71415_G) {
         if (props.getBijuuScale() < 2) {
            if (props.getbyakugan() >= 1) {
               if (!props.getByakuganActive()) {
                  if (props.getByakuganSize() == 1) {
                     props.setByakuganActive(true);
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "Byakugan!"));
                     PacketDispatcher.sendToServer(new SyncByakuganActiveMessage(player));
                     props.settrackAll(true);
                     PacketDispatcher.sendToServer(new SynctrackAllMessage(player));
                  }
               } else if (props.getByakuganActive()) {
                  props.setByakuganActive(false);
                  PacketDispatcher.sendToServer(new SyncByakuganActiveMessage(player));
                  props.settrackAll(false);
                  PacketDispatcher.sendToServer(new SynctrackAllMessage(player));
                  if (props.getByakuganSize() == 3) {
                     props.setSixPaths(false);
                     PacketDispatcher.sendToServer(new SyncSixPathsMessage(player));
                  }
               }
            }
         } else {
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "You are in Tailed beast Mode!"));
         }
      }

      if (keyBindings[3].func_151468_f() && this.mc.field_71415_G) {
         if (props.getIzanagi() != 1) {
            if (props.getsharinganicon() == 1 && props.getSharinganActive() && props.getmangekyouSharingan() >= 1) {
               if (!props.getSusanoA()) {
                  props.setSusanoA(true);
                  props.setSusanoActive(1);
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Susano'o Cage!!"));
                  PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                  PacketDispatcher.sendToServer(new SyncSusanoMessage(player));
               } else if (props.getSusanoA()) {
                  if (props.getSusanoActive() == 5) {
                     props.setSusanoActive(0);
                     props.setSusanoA(false);
                     PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     PacketDispatcher.sendToServer(new SyncSusanoMessage(player));
                  } else {
                     props.setSusanoActive(0);
                     props.setSusanoA(false);
                     PacketDispatcher.sendToServer(new SyncSusanoAMessage(player));
                     PacketDispatcher.sendToServer(new SyncSusanoMessage(player));
                  }
               }
            }
         } else {
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Your Sharingan needs to recover from Izanagi"));
         }
      }

      if (keyBindings[7].func_151468_f() && this.mc.field_71415_G) {
         if (!props.getChakraControl()) {
            props.setChakraControl(true);
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.AQUA + "Activate Chakra Control"));
            PacketDispatcher.sendToServer(new SyncChakraControlMessage(player));
         } else if (props.getChakraControl()) {
            props.setChakraControl(false);
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.AQUA + "De-Activate Chakra Control"));
            PacketDispatcher.sendToServer(new SyncChakraControlMessage(player));
         }
      }

      if (keyBindings[8].func_151468_f() && this.mc.field_71415_G) {
         if (props.getBijuuScale() < 2) {
            if (props.getCurseSeal() == 1) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(2);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 2) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(3);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(1);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(1);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 3 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(1);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }

            if (props.getCurseSeal() == 4) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(5);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 5) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(6);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(4);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(4);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 6 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(4);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }

            if (props.getCurseSeal() == 7) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(8);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 8) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(9);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(7);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(7);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 9 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(7);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }

            if (props.getCurseSeal() == 10) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(11);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 11) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(12);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(10);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(10);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 12 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(10);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }

            if (props.getCurseSeal() == 13) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(14);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 14) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(15);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(13);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(13);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 15 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(13);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }

            if (props.getCurseSeal() == 16) {
               if (props.getCurseSize() >= 1) {
                  props.setCurseSeal(17);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(true);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 1"));
               }
            } else if (props.getCurseSeal() == 17) {
               if (props.getCurseSize() >= 2) {
                  props.setCurseSeal(18);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Stage 2"));
                  if (Keyboard.isKeyDown(42)) {
                     props.setCurseSeal(16);
                     PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                     props.setCurseSealActive(false);
                     PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                     player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
                  }
               } else if (Keyboard.isKeyDown(42)) {
                  props.setCurseSeal(16);
                  PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
                  props.setCurseSealActive(false);
                  PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
               }
            } else if (props.getCurseSeal() == 18 && Keyboard.isKeyDown(42)) {
               props.setCurseSeal(16);
               PacketDispatcher.sendToServer(new SyncCurseSealMessage(player));
               props.setCurseSealActive(false);
               PacketDispatcher.sendToServer(new SyncCurseSealActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "Curse Seal Deactivate"));
            }
         } else {
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "You are in Tailed beast Mode!"));
         }
      }

      if (keyBindings[9].func_151468_f() && this.mc.field_71415_G) {
         if (props.getBijuuScale() == 1) {
            if (props.getSize() >= 1.0) {
               props.setBijuuScale(2);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(true);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Chakra Transformation"));
            }

            if (Keyboard.isKeyDown(42)) {
               props.setBijuuScale(1);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(false);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Deactivate"));
            }
         } else if (props.getBijuuScale() == 2) {
            if (props.getSize() >= 2.0) {
               props.setBijuuScale(3);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(true);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Chakra Transformation"));
            }

            if (Keyboard.isKeyDown(42)) {
               props.setBijuuScale(1);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(false);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Deactivate"));
            }
         } else if (props.getBijuuScale() == 3) {
            if (props.getSize() >= 3.0) {
               props.setBijuuScale(4);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(true);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Transformation"));
            }

            if (Keyboard.isKeyDown(42)) {
               props.setBijuuScale(1);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(false);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Deactivate"));
            }
         } else if (props.getBijuuScale() == 4) {
            if (props.getSize() >= 4.0) {
               if (props.BijuuKind == 1) {
                  props.setBijuuScale(5);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Shukaku: Let's go!"));
               } else if (props.BijuuKind == 2) {
                  props.setBijuuScale(6);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Matatabi: Let's go!"));
               } else if (props.BijuuKind == 3) {
                  props.setBijuuScale(7);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Isobu: Let's go!"));
               } else if (props.BijuuKind == 4) {
                  props.setBijuuScale(8);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Son Goku: Let's go!"));
               } else if (props.BijuuKind == 5) {
                  props.setBijuuScale(9);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Kokuo: Let's go!"));
               } else if (props.BijuuKind == 6) {
                  props.setBijuuScale(10);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Saiken: Let's go!"));
               } else if (props.BijuuKind == 7) {
                  props.setBijuuScale(11);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Chomei: Let's go!"));
               } else if (props.BijuuKind == 8) {
                  props.setBijuuScale(12);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Gyuki: Let's go!"));
               } else if (props.BijuuKind == 9) {
                  props.setBijuuScale(13);
                  PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
                  props.setBijuuFormActive(true);
                  PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.RED + "Kurama: Let's go!"));
               }
            }

            if (Keyboard.isKeyDown(42)) {
               props.setBijuuScale(1);
               PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
               props.setBijuuFormActive(false);
               PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
               player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Deactivate"));
            }
         } else if (props.getBijuuScale() >= 5 && Keyboard.isKeyDown(42)) {
            props.setBijuuScale(1);
            PacketDispatcher.sendToServer(new SyncBijuuScaleMessage(player));
            props.setBijuuFormActive(false);
            PacketDispatcher.sendToServer(new SyncBijuuFormActiveMessage(player));
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "Bijuu Deactivate"));
         }
      }

      if (keyBindings[4].func_151470_d() && this.mc.field_71415_G) {
         if (!Keyboard.isKeyDown(42)) {
            this.ChakraRestoreCounter++;
            if (this.ChakraRestoreCounter == 50) {
               PacketDispatcher.sendToServer(new SyncChakraMessage(player));
               this.ChakraRestoreCounter = 0;
            }
         }

         if (Keyboard.isKeyDown(42)) {
            this.ChakraRestoreCounter++;
            if (this.ChakraRestoreCounter == 50) {
               PacketDispatcher.sendToServer(new SyncSenChakraMessage(player));
               this.ChakraRestoreCounter = 0;
            }
         }
      }

      ItemStack heldItem = player.func_71045_bC();
      if (heldItem == null && this.mc.field_71415_G && props.getBijuuScale() >= 5 && keyBindings[16].func_151468_f()) {
         PacketDispatcher.sendToServer(new SyncBijuuSlashMessage(true, player));
      }

      if (heldItem != null && this.mc.field_71415_G) {
         if (!Keyboard.isKeyDown(42)) {
            if (props.KenjutsuSkills >= 1) {
               if (keyBindings[11].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword && this.KenjutsuCounter == 0) {
                  PacketDispatcher.sendToServer(new SyncKenjutsu2Message(true, player));
                  this.KenjutsuCounter = 100;
               }

               if (props.KenjutsuSkills >= 3) {
                  if (keyBindings[12].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword) {
                     if (props.getCurrentMana() >= 30) {
                        PacketDispatcher.sendToServer(new SyncKenjutsu3Message(true, player));
                     } else {
                        player.func_145747_a(new ChatComponentText("You do not have enough Chakra, 30 Needed"));
                     }
                  }

                  if (props.KenjutsuSkills >= 5 && keyBindings[13].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword) {
                     if (props.getCurrentMana() >= 20) {
                        PacketDispatcher.sendToServer(new SyncKenjutsu5Message(true, player));
                     } else {
                        player.func_145747_a(new ChatComponentText("You do not have enough Chakra, 20 Needed"));
                     }
                  }
               }
            }
         } else if (Keyboard.isKeyDown(42) && props.KenjutsuSkills >= 2) {
            if (keyBindings[11].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword && this.KenjutsuCounter == 0) {
               PacketDispatcher.sendToServer(new SyncKenjutsu1Message(true, player));
               this.KenjutsuCounter = 100;
            }

            if (props.KenjutsuSkills >= 4) {
               if (keyBindings[12].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword && this.KenjutsuCounter == 0) {
                  PacketDispatcher.sendToServer(new SyncKenjutsu6Message(true, player));
                  this.KenjutsuCounter = 100;
               }

               if (props.KenjutsuSkills >= 6 && keyBindings[13].func_151468_f() && heldItem.func_77973_b() instanceof ItemSword) {
                  if (props.getCurrentMana() >= 40) {
                     PacketDispatcher.sendToServer(new SyncKenjutsu4Message(true, player));
                  } else {
                     player.func_145747_a(new ChatComponentText("You do not have enough Chakra, 40 Needed"));
                  }
               }
            }
         }
      }

      if (keyBindings[14].func_151468_f() && this.mc.field_71415_G) {
         if (props.getBijuuScale() < 2) {
            if (props.getKetsuryugan() == 1) {
               if (!props.getKetsuryuganActive()) {
                  props.setKetsuryuganActive(true);
                  player.func_146105_b(new ChatComponentText(EnumChatFormatting.DARK_RED + "Ketsuryugan!"));
                  PacketDispatcher.sendToServer(new SyncKetsuryuganActiveMessage(player));
               } else if (props.getKetsuryuganActive()) {
                  props.setKetsuryuganActive(false);
                  PacketDispatcher.sendToServer(new SyncKetsuryuganActiveMessage(player));
               }
            }
         } else {
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.GOLD + "You are in Tailed Beast mode!"));
         }
      }

      if (keyBindings[15].func_151468_f() && this.mc.field_71415_G && props.getBijuuScale() < 2 && props.getSageVersion() >= 1) {
         if (!props.getSageActive()) {
            props.setSageActive(true);
            PacketDispatcher.sendToServer(new SyncSageActiveMessage(player));
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.GREEN + "Sage Mode Activate"));
         } else if (props.getSageActive()) {
            props.setSageActive(false);
            PacketDispatcher.sendToServer(new SyncSageActiveMessage(player));
            player.func_146105_b(new ChatComponentText(EnumChatFormatting.GREEN + "Sage Mode De-Activate"));
         }
      }
   }

   @SubscribeEvent
   public void OnEvent2(TickEvent event) {
      KeyBinding[] keyBindings = ClientProxy.keyBindings;
      if (keyBindings[0].func_151470_d() && this.mc.field_71415_G) {
         Minecraft.func_71410_x().func_147108_a(new GuiTest());
      }

      if (keyBindings[1].func_151470_d()) {
      }
   }
}
