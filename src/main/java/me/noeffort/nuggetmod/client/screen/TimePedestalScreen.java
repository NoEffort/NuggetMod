package me.noeffort.nuggetmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.container.TimePedestalContainer;
import me.noeffort.nuggetmod.common.container.WeatherPedestalContainer;
import me.noeffort.nuggetmod.common.tileentity.TimePedestalTileEntity;
import me.noeffort.nuggetmod.common.tileentity.WeatherPedestalTileEntity;
import me.noeffort.nuggetmod.core.network.TimePedestalUpdateMessage;
import me.noeffort.nuggetmod.core.network.WeatherPedestalUpdateMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TimePedestalScreen extends ContainerScreen<TimePedestalContainer> {

    private final ResourceLocation screen;
    private final PlayerEntity player;

    public TimePedestalScreen(TimePedestalContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.screen = NuggetMod.location("textures/gui/time_pedestal.png");
        this.player = inventory.player;

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(this.placeAt(new ImageButton(0, 0, 50, 50, 0, 0, 0,
                NuggetMod.location("textures/gui/buttons/day.png"), 50, 50, (b) ->
                this.update(TimePedestalTileEntity.Time.DAY)), 8, 18));
        this.addButton(this.placeAt(new ImageButton(0, 0, 50, 50, 0, 0, 0,
                NuggetMod.location("textures/gui/buttons/night.png"), 50, 50, (b) ->
                this.update(TimePedestalTileEntity.Time.NIGHT)), 63, 18));
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

    private void update(TimePedestalTileEntity.Time time) {
        NuggetMod.CHANNEL.sendToServer(new TimePedestalUpdateMessage(time));
        this.player.sendMessage(new StringTextComponent("Time set to: ").withStyle(TextFormatting.GREEN)
                .append(new StringTextComponent((time.name().equals("NIGHT")) ? "Night" : "Day")
                        .withStyle(TextFormatting.GOLD)), UUID.randomUUID());
    }

}
