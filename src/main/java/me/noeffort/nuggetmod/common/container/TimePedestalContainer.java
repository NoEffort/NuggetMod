package me.noeffort.nuggetmod.common.container;

import me.noeffort.nuggetmod.core.init.ContainerTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class TimePedestalContainer extends Container {

    public static TimePedestalContainer from(int id, PlayerInventory inventory, PacketBuffer buf) {
        return new TimePedestalContainer(id, inventory);
    }

    public TimePedestalContainer(int id, PlayerInventory inventory) {
        super(ContainerTypeInit.TIME_PEDESTAL_CONTAINER_TYPE.get(), id);
        ContainerHelper.addPlayerInventory(this::addSlot, inventory, 8, 84);
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return true;
    }

}
