package me.noeffort.nuggetmod.common.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

import java.util.function.Consumer;

public class ContainerHelper {

    public static void addPlayerInventory(Consumer<Slot> slot, IInventory inventory, int x, int y) {
        addPlayerInventory(slot, inventory, x, y, Slot::new);
    }

    public static void addPlayerInventory(Consumer<Slot> slot, IInventory inventory, int x, int y, SlotCreator creator) {
        int size = 18;
        int rows = 3;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < 9; j++) {
                slot.accept(creator.create(inventory, j + i * 9 + 9, x + j * size, y + i * size));
            }
        }

        y = y + size * rows + 4;

        for(int i = 0; i < PlayerInventory.getSelectionSize(); i++) {
            slot.accept(creator.create(inventory, i, x + i * size, y));
        }
    }

    @FunctionalInterface
    public interface SlotCreator {
        Slot create(IInventory inventory, int index, int x, int y);
    }

}
