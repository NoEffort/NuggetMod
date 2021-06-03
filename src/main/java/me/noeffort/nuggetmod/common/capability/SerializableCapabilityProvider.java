package me.noeffort.nuggetmod.common.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class SerializableCapabilityProvider<U> extends SimpleCapabilityProvider<U> implements INBTSerializable<INBT> {

    public SerializableCapabilityProvider(final Capability<U> capability, @Nullable final Direction facing) {
        this(capability, facing, capability.getDefaultInstance());
    }

    public SerializableCapabilityProvider(final Capability<U> capability, @Nullable final Direction facing, @Nullable final U instance) {
        super(capability, facing, instance);
    }

    @Nullable
    @Override
    public INBT serializeNBT() {
        final U instance = this.getInstance();
        return (instance == null) ? null : this.getCapability().writeNBT(instance, this.getFacing());
    }

    @Override
    public void deserializeNBT(final INBT nbt) {
        final U instance = this.getInstance();
        if(instance == null) return;
        this.getCapability().readNBT(instance, this.getFacing(), nbt);
    }

}
