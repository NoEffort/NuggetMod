package me.noeffort.nuggetmod.common.refinedstorage;

import com.refinedmods.refinedstorage.api.network.IWirelessTransmitter;
import com.refinedmods.refinedstorage.apiimpl.network.node.NetworkNode;
import me.noeffort.nuggetmod.NuggetMod;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class CreativeWirelessTransmitterNetworkNode extends NetworkNode implements IWirelessTransmitter, ICreativeNetworkNode {

    public static final ResourceLocation NODE_ID = NuggetMod.location("creative_wireless_transmitter");

    public CreativeWirelessTransmitterNetworkNode(World world, BlockPos pos) {
        super(world, pos);
    }

    @Override
    public ResourceLocation getId() {
        return NODE_ID;
    }

    public boolean isCreative() {
        return true;
    }

    @Override
    public int getRange() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }

    @Override
    public IItemHandler getDrops() {
        return null;
    }

    @Override
    public BlockPos getOrigin() {
        return this.pos;
    }

    @Override
    public RegistryKey<World> getDimension() {
        return this.world.dimension();
    }

    @Override
    protected boolean canConduct(Direction direction) {
        return Direction.DOWN.equals(direction);
    }

    @Override
    public void visit(Operator operator) {
        operator.apply(world, pos.offset(Direction.DOWN.getNormal()), Direction.UP);
    }
}
