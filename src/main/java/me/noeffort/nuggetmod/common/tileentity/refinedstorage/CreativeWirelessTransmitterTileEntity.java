package me.noeffort.nuggetmod.common.tileentity.refinedstorage;

import com.refinedmods.refinedstorage.tile.NetworkNodeTile;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;
import me.noeffort.nuggetmod.common.refinedstorage.CreativeWirelessTransmitterNetworkNode;
import me.noeffort.nuggetmod.core.init.TileEntityTypeInit;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class CreativeWirelessTransmitterTileEntity extends NetworkNodeTile<CreativeWirelessTransmitterNetworkNode> {

    public static final TileDataParameter<Integer, CreativeWirelessTransmitterTileEntity> RANGE =
            new TileDataParameter<>(DataSerializers.INT, 0, t -> t.getNode().getRange());

    public CreativeWirelessTransmitterTileEntity() {
        super(TileEntityTypeInit.CREATIVE_WIRELESS_TRANSMITTER_TILE_ENTITY);
        dataManager.addWatchedParameter(RANGE);
    }

    @Override
    @Nonnull
    public CreativeWirelessTransmitterNetworkNode createNode(World world, BlockPos pos) {
        return new CreativeWirelessTransmitterNetworkNode(world, pos);
    }

}
