package com.computery.balancedflightplus;

public class AllLangMessages
{
    public static void init() {
        BalancedFlight.CREATE_REGISTRATE.addLang("tooltip", BalancedFlight.FLIGHT_ANCHOR_BLOCK.getId(), "Allows flight in a 25 block radius. Only works in the overworld.");
        BalancedFlight.CREATE_REGISTRATE.addLang("tooltip", BalancedFlight.FLIGHT_DISRUPTOR_BLOCK.getId(), "Allows flight in a 25 block radius. Only works in the overworld.");
        BalancedFlight.CREATE_REGISTRATE.addLang("tooltip", BalancedFlight.PLACEMENT_DISRUPTOR_BLOCK.getId(), "Allows flight in a 25 block radius. Only works in the overworld.");
        BalancedFlight.CREATE_REGISTRATE.addLang("tooltip", BalancedFlight.ASCENDED_FLIGHT_RING.getId(), "An incredibly dense golden ring, and yet, it allows flight anywhere.");
    }
}
