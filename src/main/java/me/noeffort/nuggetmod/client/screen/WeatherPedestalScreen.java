package me.noeffort.nuggetmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.org.apache.xpath.internal.operations.String;
import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.container.TravelBagContainer;
import me.noeffort.nuggetmod.common.container.WeatherPedestalContainer;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.UUID;

public class WeatherPedestalScreen extends ContainerScreen<WeatherPedestalContainer> {

    private final ResourceLocation screen;
    private final BlockPos pos;
    private final PlayerEntity player;

    public WeatherPedestalScreen(WeatherPedestalContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.screen = new ResourceLocation(NuggetMod.MOD_ID, "textures/gui/weather_pedestal.png");
        this.pos = container.getBlockPos();
        this.player = inventory.player;

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(this.placeAt(new Button(0, 0, 50, 50,
                new StringTextComponent("S"), (b) -> this.update(false, false, true)), 8, 18));
        this.addButton(this.placeAt(new Button(0, 0, 50, 50,
                new StringTextComponent("R"), (b) -> this.update(true, false, false)), 63, 18));
        this.addButton(this.placeAt(new Button(0, 0, 50, 50,
                new StringTextComponent("T"), (b) -> this.update(false, true, false)), 119, 18));
    }

    @Override
    public void render(@Nonnull MatrixStack matrix, int mx, int my, float ticks) {
        this.renderBackground(matrix);
        super.render(matrix, mx, my, ticks);
        this.renderTooltip(matrix, mx, my);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrix, float ticks, int mx, int my) {
        RenderSystem.clearColor(1F, 1F, 1F, 1F);
        Minecraft.getInstance().textureManager.bind(this.screen);
        this.blit(matrix, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
    }

    private <T extends Widget> T placeAt(T object, int x, int y) {
        object.x = this.getGuiLeft() + x;
        object.y = this.getGuiTop() + y;
        return object;
    }

    private void update(boolean rain, boolean thunder, boolean clear) {
        World world = this.player.level;
        TileEntity tile = world.getBlockEntity(this.pos);
        if(tile == null) {
            NuggetMod.LOGGER.warn("Attempt to update Weather Pedestal failed! Block no longer exists at: " + this.pos);
            return;
        }
        if(!(tile instanceof WeatherPedestalTileEntity)) return;
        WeatherPedestalTileEntity pedestal = (WeatherPedestalTileEntity) tile;
        pedestal.setRaining(rain);
        pedestal.setThundering(thunder);
        pedestal.setClear(clear);
        tile.getUpdateTag();
        tile.setChanged();
        this.player.sendMessage(new StringTextComponent("Weather set to: ").withStyle(TextFormatting.GREEN)
                .append(new StringTextComponent((rain) ? "Rain" : (thunder) ? "Thunder" : "Clear").withStyle(TextFormatting.GOLD)),
                UUID.randomUUID());
    }

}
