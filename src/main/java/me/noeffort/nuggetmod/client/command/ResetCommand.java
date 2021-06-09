package me.noeffort.nuggetmod.client.command;

import com.mojang.brigadier.CommandDispatcher;
import me.noeffort.nuggetmod.common.capability.world.HasBlockCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;

public class ResetCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("reset").executes((x) -> {
            ServerPlayerEntity player = x.getSource().getPlayerOrException();
            HasBlockCapability.setBlockInfo(player.level, new ArrayList<>());
            return 0;
        }));
    }

}
