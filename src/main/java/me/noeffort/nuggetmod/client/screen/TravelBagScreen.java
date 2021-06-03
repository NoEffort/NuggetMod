package me.noeffort.nuggetmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.container.TravelBagContainer;
import me.noeffort.nuggetmod.common.item.TravelBagItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class TravelBagScreen extends ContainerScreen<TravelBagContainer> {

    private final ResourceLocation screen;

    public TravelBagScreen(TravelBagContainer container, PlayerInventory inventory, ITextComponent title, TravelBagItem.Type type) {
        super(container, inventory, title);
        this.screen = new ResourceLocation(NuggetMod.MOD_ID,
                "textures/gui/travel_bag_" + type.getSerializedName() + ".png");

        this.imageWidth = type.getImageWidth();
        this.imageHeight = type.getImageHeight();

        this.inventoryLabelY = type.getImageHeight() - 92;
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

}
