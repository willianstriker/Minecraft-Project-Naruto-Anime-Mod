package com.sekwah.narutomod.client.keybinds;

import com.sekwah.narutomod.client.keybinds.KeyBindingTickHeld.KeyState;
import net.minecraft.client.KeyMapping;

public class KeyBindingTickHeld extends KeyMapping {
   boolean hasConsumedClickState = false;
   int heldTicks = 0;
   private Runnable onClick = null;

   public KeyBindingTickHeld(String name, int keyCode, String category) {
      super(name, keyCode, category);
   }

   public void registerClickConsumer(Runnable runnable) {
      this.onClick = runnable;
   }

   public void update() {
      if (this.m_90857_()) {
         this.heldTicks++;
      }

      if (this.onClick != null) {
         KeyState clickState = this.consumeClickState();
         if (clickState == KeyState.CLICK) {
            this.onClick.run();
         }
      }
   }

   public KeyState consumeClickState() {
      if (this.m_90857_()) {
         if (this.hasConsumedClickState) {
            return KeyState.HELD;
         } else {
            this.hasConsumedClickState = true;
            return KeyState.CLICK;
         }
      } else if (this.f_90818_ == 0) {
         return KeyState.NOT_PRESSED;
      } else {
         this.f_90818_--;
         return KeyState.CLICK;
      }
   }

   public int consumeReleaseDuration() {
      if (this.m_90857_()) {
         return 0;
      } else {
         int returnValue = this.heldTicks;
         this.heldTicks = 0;
         return returnValue;
      }
   }

   public int currentHeldValue() {
      return this.heldTicks;
   }

   public boolean isCurrentlyHeld() {
      return this.heldTicks > 20 && this.m_90857_();
   }

   public void m_7249_(boolean down) {
      if (this.m_90857_() && !down) {
         if (this.hasConsumedClickState && this.f_90818_ > 0) {
            this.f_90818_ = 0;
         }

         this.hasConsumedClickState = false;
      } else if (!this.m_90857_() && down) {
         this.heldTicks = 0;
      }

      super.m_7249_(down);
   }
}
