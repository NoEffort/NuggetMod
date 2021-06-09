package me.noeffort.nuggetmod.common.container;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.core.init.ContainerTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TravelBagContainer extends Container {

    private final TravelBagItem.Type type;
    private final int blocked;

    public static TravelBagContainer from(int id, TravelBagItem.Type type, PlayerInventory inventory, PacketBuffer buffer) {
        return new TravelBagContainer(id, type, inventory, new ItemStackHandler(TravelBagItem.getSize(type)), buffer.readEnum(Hand.class));
    }

    public TravelBagContainer(int id, TravelBagItem.Type type, PlayerInventory inventory, IItemHandlerModifiable bag, Hand hand) {
        super(ContainerTypeInit.getTravelBagContainer(type), id);
        this.type = type;

        // Backpack Inventory
        for(int i = 0; i < type.getRows(); i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new SlotItemHandler(bag, j + i * 9, 8 + j * 18, 17 + i * 18));
            }
        }

        // Player Inventory
        switch(type) {
            case IRON:
                ContainerHelper.addPlayerInventory(this::addSlot, inventory, 8, 102);
                break;
            case GOLD:
                ContainerHelper.addPlayerInventory(this::addSlot, inventory, 8, 120);
                break;
            case DIAMOND:
                ContainerHelper.addPlayerInventory(this::addSlot, inventory, 8, 138);
                break;
            case DEFAULT:
            default:
                ContainerHelper.addPlayerInventory(this::addSlot, inventory, 8, 84);
                break;
        }

        int slots = this.type.getSize() + inventory.items.size();
        this.blocked = (hand == Hand.MAIN_HAND) ? (slots - 1) - (8 - inventory.selected) : -1;
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull PlayerEntity player, int index) {
        Slot slot = this.getSlot(index);

        if(!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack item = slot.getItem();
        ItemStack copy = item.copy();

        if(item.getItem() instanceof TravelBagItem) return ItemStack.EMPTY;

        int size = TravelBagItem.getSize(this.type);

        if(index < size && !this.moveItemStackTo(item, size, this.slots.size(), true))
            return ItemStack.EMPTY;

        if(!this.moveItemStackTo(item, 0, size, false))
            return ItemStack.EMPTY;

        if(item.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    @Nonnull
    @Override
    public ItemStack clicked(int index, int drag, @Nonnull ClickType type, @Nonnull PlayerEntity player) {
        if(index >= 0 && index == this.blocked) {
            return ItemStack.EMPTY;
        }
        return super.clicked(index, drag, type, player);
    }
}
