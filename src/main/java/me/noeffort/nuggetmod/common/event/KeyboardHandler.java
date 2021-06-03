package me.noeffort.nuggetmod.common.event;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.core.init.KeybindInit;
import me.noeffort.nuggetmod.core.network.TravelBagOpenMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyboardHandler {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if(Minecraft.getInstance().level == null) return;
        onInput(Minecraft.getInstance(), event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        if(Minecraft.getInstance().level == null) return;
        onInput(Minecraft.getInstance(), event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft minecraft, int key, int action) {
        for(TravelBagItem.Type type : TravelBagItem.Type.values()) {
            ItemStack item = TravelBagItem.findInCurios(type, minecraft.player);
            if(minecraft.screen == null && KeybindInit.OPEN_TRAVEL_BAG.isDown() && !item.equals(ItemStack.EMPTY)) {
                NuggetMod.CHANNEL.sendToServer(new TravelBagOpenMessage(item));
            }
        }
    }

}
