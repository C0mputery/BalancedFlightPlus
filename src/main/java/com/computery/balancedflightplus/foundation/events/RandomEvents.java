package com.computery.balancedflightplus.foundation.events;

import com.computery.balancedflightplus.content.flightAnchor.FlightController;
import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity;
import com.computery.balancedflightplus.foundation.config.BalancedFlightConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RandomEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void entityPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (IsBeingDisrupted(event.getPos())) {
                // Check if player is in creative or spectator
                if (FlightController.isInCreativeOrSpectator(player)) {
                    return;
                }
                event.setCanceled(true);
                forceHeldItemSync(player, InteractionHand.MAIN_HAND);
            }
        }
    }

    public static boolean IsBeingDisrupted(BlockPos pos)
    {
        double disruptorsDistanceMultiplier = BalancedFlightConfig.disruptorDistanceMultiplier.get();

        return PlacementDisruptorEntity.ActiveDisruptors
                .entrySet()
                .stream()
                .anyMatch(anchor -> distSqr(anchor.getKey(), pos.getCenter()) < (disruptorsDistanceMultiplier * (anchor.getValue().getSpeed() / 4 ) * (disruptorsDistanceMultiplier * anchor.getValue().getSpeed() / 4)));
    }
    private static double distSqr(Vec3i vec, Vec3 other) {
        double d1 = (double)vec.getX() - other.x;
        double d3 = (double)vec.getZ() - other.z;
        return d1 * d1 + d3 * d3;
    }

    // Stolen from FTB Chunks!
    public static void forceHeldItemSync(ServerPlayer sp, InteractionHand hand) {
        switch (hand) {
            case MAIN_HAND -> sp.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, sp.getInventory().selected, sp.getItemInHand(hand)));
            case OFF_HAND -> sp.connection.send(new ClientboundContainerSetSlotPacket(-2, 0, Inventory.SLOT_OFFHAND, sp.getItemInHand(hand)));
        }
    }
}
