package me.noeffort.nuggetmod.common.event;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.capability.travelbag.ITravelBag;
import me.noeffort.nuggetmod.common.capability.travelbag.TravelBag;
import me.noeffort.nuggetmod.common.capability.travelbag.TravelBagCapability;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.common.item.TravelBagUpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftingHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event) {
        if(event.getResult().equals(Event.Result.DENY) || event.isCanceled()) return;
        ItemStack bag = find(event.getInventory(), TravelBagItem.class).orElse(ItemStack.EMPTY);
        ItemStack upgrade = find(event.getInventory(), TravelBagUpgradeItem.class).orElse(ItemStack.EMPTY);
        ItemStack result = event.getCrafting();
        NuggetMod.LOGGER.debug("Input NBT: " + bag.serializeNBT());
        if(!bag.equals(ItemStack.EMPTY) && !upgrade.equals(ItemStack.EMPTY)) {
            ITravelBag info = TravelBagCapability.getBagInfo(bag).resolve().orElse(new TravelBag());
            TravelBagItem.Type type = ((TravelBagUpgradeItem) upgrade.getItem()).getType().getTo();
            ItemStackHandler handler = new ItemStackHandler(type.getSize());
            for(int i = 0; i < info.getHandler().getSlots(); i++) {
                handler.setStackInSlot(i, info.getHandler().getStackInSlot(i));
            }
            TravelBagCapability.setBagInfo(result, handler, type);
            NuggetMod.LOGGER.debug("Output NBT: " + result.serializeNBT());
        }
    }

    private static Optional<ItemStack> find(IInventory inventory, Class<? extends Item> clazz) {
        for(int i = 0; i < inventory.getContainerSize(); i++) {
            if(clazz.isInstance(inventory.getItem(i).getItem())) {
                return Optional.of(inventory.getItem(i));
            }
        }
        return Optional.of(ItemStack.EMPTY);
    }

}
