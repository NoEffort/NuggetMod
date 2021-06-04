package me.noeffort.nuggetmod.common.capability.world;

import me.noeffort.nuggetmod.common.capability.SerializableCapabilityProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class HasBlockCapability {

    @CapabilityInject(IHasBlock.class)
    public static final Capability<IHasBlock> CAPABILITY_HAS_BLOCK = null;
    public static final Direction DEFAULT_FACING = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHasBlock.class, new Capability.IStorage<IHasBlock>() {
            @Nonnull
            @Override
            public INBT writeNBT(Capability<IHasBlock> capability, IHasBlock instance, Direction side) {
                CompoundNBT tag = new CompoundNBT();
                List<String> blocks = instance.getBlocks();
                CompoundNBT compound = new CompoundNBT();
                for(int i = 0; i < blocks.size(); i++) {
                    compound.putString(String.valueOf(i), blocks.get(i));
                }
                tag.putInt("BlockListSize", blocks.size());
                tag.put("GlobalBlockList", compound);
                return tag;
            }

            @Override
            public void readNBT(Capability<IHasBlock> capability, IHasBlock instance, Direction side, INBT nbt) {
                CompoundNBT tag = (CompoundNBT) nbt;
                List<String> blocks = new ArrayList<>();
                CompoundNBT compound = tag.getCompound("GlobalBlockList");
                int size = tag.getInt("BlockListSize");
                for(int i = 0; i < size; i++) {
                    blocks.add(compound.getString(String.valueOf(i)));
                }
                instance.setBlocks(blocks);
            }
        }, HasBlock::new);
    }

    public static LazyOptional<IHasBlock> getBlockPosInfo(final World world) {
        return world.getCapability(CAPABILITY_HAS_BLOCK, DEFAULT_FACING);
    }

    public static void addBlockInfo(final World world, final String block) {
        getBlockPosInfo(world).ifPresent((h) -> h.addBlock(block));
    }

    public static void removeBlockInfo(final World world, final String block) {
        getBlockPosInfo(world).ifPresent((h) -> h.removeBlock(block));
    }

    public static void setBlockInfo(final World world, final List<String> blocks) {
        getBlockPosInfo(world).ifPresent((h) -> h.setBlocks(blocks));
    }

    public static ICapabilityProvider createProvider() {
        return new SerializableCapabilityProvider<>(CAPABILITY_HAS_BLOCK, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(final IHasBlock block) {
        return new SerializableCapabilityProvider<>(CAPABILITY_HAS_BLOCK, DEFAULT_FACING, block);
    }

}
