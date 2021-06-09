package me.noeffort.nuggetmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

public class PlayerHelper {

    @Nullable
    public static BlockPos getLookingAt(PlayerEntity player) {
        return getLookingAt(player, 16);
    }

    @Nullable
    public static BlockPos getLookingAt(PlayerEntity player, int distance) {
        RayTraceResult ray = Minecraft.getInstance().hitResult;

        if(ray == null) return null;

        double x = ray.getLocation().x;
        double y = ray.getLocation().y;
        double z = ray.getLocation().z;

        double xa = player.getLookAngle().x;
        double ya = player.getLookAngle().y;
        double za = player.getLookAngle().z;

        if((x % 1 == 0) && xa < 0) x -= 0.01;
        if((y % 1 == 0) && ya < 0) y -= 0.01;
        if((z % 1 == 0) && za < 0) z -= 0.01;

        return new BlockPos(x, y, z);
    }

    public static boolean hasItem(PlayerEntity player, Item item) {
        return player.inventory.contains(new ItemStack(item)) ||
                CuriosApi.getCuriosHelper().findEquippedCurio(item, player).isPresent();
    }

    @Nullable
    public static PlayerEntity fromEntity(Entity entity) {
        return (!(entity instanceof LivingEntity)) ? null : fromEntity((LivingEntity) entity);
    }

    @Nullable
    public static PlayerEntity fromEntity(LivingEntity living) {
        return (!(living instanceof PlayerEntity)) ? null : (PlayerEntity) living;
    }

}
