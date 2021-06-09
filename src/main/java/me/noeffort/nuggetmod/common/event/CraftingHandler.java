package me.noeffort.nuggetmod.common.event;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.common.item.TravelBagUpgradeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftingHandler {

    @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
    public static void onItemCraft(PlayerEvent.ItemCraftedEvent event) {
        if(event.getResult().equals(Event.Result.DENY) || event.isCanceled()) return;
        PlayerEntity player = event.getPlayer();
        ItemStack bag = find(event.getInventory(), TravelBagItem.class).orElse(ItemStack.EMPTY);
        ItemStack upgrade = find(event.getInventory(), TravelBagUpgradeItem.class).orElse(ItemStack.EMPTY);
//        ItemStack result = event.getCrafting();
        if(!bag.equals(ItemStack.EMPTY) && !upgrade.equals(ItemStack.EMPTY)) {
            event.getInventory().clearContent();
            player.inventory.setCarried(ItemStack.EMPTY);
            player.inventory.setItem(player.inventory.getFreeSlot(), ItemStack.EMPTY);
            player.addItem(bag);
            player.addItem(upgrade);
            event.setResult(Event.Result.DENY);
//            ITravelBag info = TravelBagCapability.getBagInfo(bag).resolve().orElse(new TravelBag());
//            TravelBagItem.Type type = ((TravelBagUpgradeItem) upgrade.getItem()).getType().getTo();
//            ItemStackHandler handler = new ItemStackHandler(type.getSize());
//            for(int i = 0; i < info.getHandler().getSlots(); i++) {
//                handler.setStackInSlot(i, info.getHandler().getStackInSlot(i));
//            }
//            TravelBagCapability.setBagInfo(result, handler, type);
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
