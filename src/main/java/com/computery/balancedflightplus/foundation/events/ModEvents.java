package com.computery.balancedflightplus.foundation.events;

import com.computery.balancedflightplus.foundation.network.BalancedFlightNetwork;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents
{
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(BalancedFlightNetwork::registerMessage);
    }
}
