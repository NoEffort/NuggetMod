package me.noeffort.nuggetmod.common.item.curio;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.core.init.ItemInit;
import me.noeffort.nuggetmod.core.network.PickBlockMessage;
import me.noeffort.nuggetmod.util.Format;
import me.noeffort.nuggetmod.util.PlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GodCharm extends Item {

    public GodCharm(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack item, @Nullable World world, @Nonnull List<ITextComponent> tooltip,
                                @Nonnull ITooltipFlag flag) {
        tooltip.add(Format.translate(Format.Type.TOOLTIP, "god_charm")
                .withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.DARK_GRAY));
        super.appendHoverText(item, world, tooltip, flag);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack item, @Nonnull World world, @Nonnull Entity entity, int slot, boolean c) {
        PlayerEntity player = PlayerHelper.fromEntity(entity);
        if(player == null || !player.level.isClientSide()) return;
        if(!PlayerHelper.hasItem(player, ItemInit.GOD_CHARM.get()))
            return;
        BlockPos pos = player.blockPosition();
        BlockState state = player.level.getBlockState(pos.below());
        if(state.getMaterial().isLiquid() && player.level.isEmptyBlock(pos)) {
            if(!player.isCrouching()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0, 1));
                player.fallDistance = 0.0F;
                player.setOnGround(true);
            }
        }
        super.inventoryTick(item, world, entity, slot, c);
    }

    @SubscribeEvent
    public static void onMiddleClick(InputEvent.MouseInputEvent event) {
        if(event.getAction() != GLFW.GLFW_PRESS) return;
        PlayerEntity player = Minecraft.getInstance().player;
        if(player == null) return;
        ItemStack charm = new ItemStack(ItemInit.GOD_CHARM.get());
        if(event.getButton() == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            if(!PlayerHelper.hasItem(player, charm.getItem()))
                return;
            if(player.isCreative()) return;
            BlockPos pos = PlayerHelper.getLookingAt(player);
            if(pos == null) return;
            Block block = player.level.getBlockState(pos).getBlock();
            ItemStack item = new ItemStack(block);
            NuggetMod.CHANNEL.sendToServer(new PickBlockMessage(item));
        }
    }

    // Replace with insanely high health & constant mid-tier regen
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onAttackEntity(LivingAttackEvent event) {
        PlayerEntity player = PlayerHelper.fromEntity(event.getEntityLiving());
        if(player == null || player.level.isClientSide()) return;
        if(!PlayerHelper.hasItem(player, ItemInit.GOD_CHARM.get()))
            return;
        if(event.getSource().equals(DamageSource.OUT_OF_WORLD)) return;
        event.setCanceled(true);
    }

}
