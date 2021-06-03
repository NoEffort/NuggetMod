package me.noeffort.nuggetmod.core.init;

import me.noeffort.nuggetmod.NuggetMod;
import net.minecraft.client.settings.KeyBinding;

import java.awt.event.KeyEvent;

public class KeybindInit {

    public static final KeyBinding OPEN_TRAVEL_BAG = new KeyBinding("key." + NuggetMod.MOD_ID + ".open_travel_bag",
            KeyEvent.VK_B, "key.category." + NuggetMod.MOD_ID);

}
