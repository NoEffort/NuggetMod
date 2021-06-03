package me.noeffort.nuggetmod.common.tileentity;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeatherPedestalTileEntity extends TileEntity implements ITickableTileEntity {

    private boolean raining = false;
    private boolean thundering = false;
    private boolean clear = true;

    public WeatherPedestalTileEntity() {
        this(TileEntityTypeInit.WEATHER_PEDESTAL_TILE_ENTITY.get());
    }

    public WeatherPedestalTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setThundering(boolean thundering) {
        this.thundering = thundering;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt = super.save(nbt);
        nbt.putBoolean("WeatherRain", this.raining);
        nbt.putBoolean("WeatherThunder", this.thundering);
        nbt.putBoolean("WeatherClear", this.clear);
        return nbt;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.raining = nbt.getBoolean("WeatherRain");
        this.thundering = nbt.getBoolean("WeatherThunder");
        this.clear = nbt.getBoolean("WeatherClear");
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 1, this.getUpdateTag());
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
        NuggetMod.LOGGER.debug(String.format("Values: [Rain: %s, Thunder: %s, Clear: %s]", this.raining, this.thundering, this.clear));
        if(this.level == null) throw new IllegalStateException("Block cannot be in a null world!");
        IWorldInfo info = this.level.getLevelData();
        if(this.raining) info.setRaining(true);
        if(this.thundering) info.setRaining(true);
        if(this.clear) info.setRaining(false);
    }

}
