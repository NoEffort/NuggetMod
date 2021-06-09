package me.noeffort.nuggetmod.common.event;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.client.command.ResetCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandHandler {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        ResetCommand.register(event.getDispatcher());
    }

}
