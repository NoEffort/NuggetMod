package me.noeffort.nuggetmod.common.block;

import me.noeffort.nuggetmod.common.container.WeatherPedestalContainer;
import me.noeffort.nuggetmod.core.init.TileEntityTypeInit;
import me.noeffort.nuggetmod.util.Format;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeatherPedestalBlock extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.FACING;

    public WeatherPedestalBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F).harvestTool(ToolType.PICKAXE));
        this.defaultBlockState().setValue(FACING, Direction.NORTH);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypeInit.WEATHER_PEDESTAL_TILE_ENTITY.get().create();
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> container) {
        super.createBlockStateDefinition(container);
        container.add(FACING);
    }

    @Nonnull
    @Override
    public BlockState mirror(@Nonnull BlockState state, @Nonnull Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos,
                                @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if(!world.isClientSide()) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new ContainerProvider(pos), pos);
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    private static class ContainerProvider implements INamedContainerProvider {

        private final BlockPos pos;

        public ContainerProvider(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        @Nonnull
        public ITextComponent getDisplayName() {
            return Format.translate(Format.Type.CONTAINER, "weather_pedestal");
        }

        @Nullable
        @Override
        public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
            return new WeatherPedestalContainer(id, inventory, this.pos);
        }

    }
}
