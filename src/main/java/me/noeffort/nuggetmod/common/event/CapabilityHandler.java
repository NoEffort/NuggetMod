package me.noeffort.nuggetmod.common.event;

import me.noeffort.nuggetmod.NuggetMod;
import me.noeffort.nuggetmod.common.capability.world.HasBlock;
import me.noeffort.nuggetmod.common.capability.world.HasBlockCapability;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuggetMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<World> event) {
        event.addCapability(NuggetMod.location("capability_has_block"), HasBlockCapability.CAPABILITY_HAS_BLOCK == null ?
                null : HasBlockCapability.createProvider());
    }

}
