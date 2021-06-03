package me.noeffort.nuggetmod.common.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCapabilityProvider<U> implements ICapabilityProvider {

    protected final Capability<U> capability;
    protected final Direction facing;
    protected final U instance;
    protected final LazyOptional<U> lazyOptional;

    public SimpleCapabilityProvider(final Capability<U> capability, @Nullable final Direction facing, @Nullable final U instance) {
        this.capability = capability;
        this.facing = facing;
        this.instance = instance;
        this.lazyOptional = (instance != null) ? LazyOptional.of(() -> instance) : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return this.getCapability().orEmpty(cap, this.lazyOptional);
    }

    public final Capability<U> getCapability() {
        return this.capability;
    }

    @Nullable
    public Direction getFacing() {
        return this.facing;
    }

    @Nullable
    public final U getInstance() {
        return this.instance;
    }

}
