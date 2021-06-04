package me.noeffort.nuggetmod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class PlayerHelper {

    @Nullable
    public static BlockPos getLookingAt(PlayerEntity player) {
        return getLookingAt(player, 16);
    }

    @Nullable
    public static BlockPos getLookingAt(PlayerEntity player, int distance) {
        Vector3d path = player.getViewVector(0).scale(distance);
        Vector3d eye = player.getEyePosition(0);
        RayTraceResult result = player.level.clip(new RayTraceContext(eye, eye.add(path), RayTraceContext.BlockMode.OUTLINE,
                RayTraceContext.FluidMode.ANY, null));
        return result.getType().equals(RayTraceResult.Type.MISS) ? null : new BlockPos(result.getLocation());
    }

}
