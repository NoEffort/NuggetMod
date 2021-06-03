package me.noeffort.nuggetmod.common.capability.travelbag;

import me.noeffort.nuggetmod.common.capability.SerializableCapabilityProvider;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TravelBagCapability {

    @CapabilityInject(ITravelBag.class)
    public static final Capability<ITravelBag> CAPABILITY_TRAVEL_BAG = null;
    public static final Direction DEFAULT_FACING = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ITravelBag.class, new Capability.IStorage<ITravelBag>() {
            @Nonnull
            @Override
            public INBT writeNBT(Capability<ITravelBag> capability, ITravelBag instance, Direction side) {
                CompoundNBT tag = new CompoundNBT();
                tag.put("Contents", instance.getHandler().serializeNBT());
                tag.putString("Type", instance.getType().name());
                return tag;
            }

            @Override
            public void readNBT(Capability<ITravelBag> capability, ITravelBag instance, Direction side, INBT nbt) {
                CompoundNBT tag = (CompoundNBT) nbt;
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(tag.getCompound("Contents"));
                instance.setHandler(handler);
                instance.setType(TravelBagItem.Type.valueOf(tag.getString("Type")));
            }
        }, TravelBag::new);
    }

    public static LazyOptional<ITravelBag> getBagInfo(final ItemStack item) {
        return item.getCapability(CAPABILITY_TRAVEL_BAG, DEFAULT_FACING);
    }

    public static void setBagInfo(final ItemStack item, final ItemStackHandler handler, final TravelBagItem.Type type) {
        getBagInfo(item).ifPresent((h) -> {
            h.setHandler(handler);
            h.setType(type);
        });
    }

    public static ICapabilityProvider createProvider() {
        return new SerializableCapabilityProvider<>(CAPABILITY_TRAVEL_BAG, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(final ITravelBag bag) {
        return new SerializableCapabilityProvider<>(CAPABILITY_TRAVEL_BAG, DEFAULT_FACING, bag);
    }

}
