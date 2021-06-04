package me.noeffort.nuggetmod.common.tileentity;

import me.noeffort.nuggetmod.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimePedestalTileEntity extends TileEntity implements ITickableTileEntity {

    private Time time = Time.DAY;

    public TimePedestalTileEntity() {
        this(TileEntityTypeInit.TIME_PEDESTAL_TILE_ENTITY.get());
    }

    public TimePedestalTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt = super.save(nbt);
        nbt.putString("Time", time.name());
        return nbt;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.time = Time.valueOf(nbt.getString("Time"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), -1, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(this.getBlockState(), pkt.getTag());
    }

    @Override
    public void tick() {
        if(this.level == null) throw new IllegalStateException("Block cannot be in a null world!");
        switch(this.time) {
            case NIGHT:
            case DAY:
            default:
                this.level.getDayTime();
        }
    }

    public enum Time {

        DAY,
        NIGHT

    }

}
