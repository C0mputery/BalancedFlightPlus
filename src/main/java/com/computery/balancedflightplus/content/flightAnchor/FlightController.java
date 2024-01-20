package com.computery.balancedflightplus.content.flightAnchor;

import com.computery.balancedflightplus.content.flightAnchor.entity.FlightAnchorEntity;
import com.computery.balancedflightplus.content.flightDisruptor.entity.FlightDisruptorEntity;
import com.computery.balancedflightplus.foundation.compat.AscendedRingCurio;
import com.computery.balancedflightplus.foundation.config.BalancedFlightConfig;
import net.minecraft.core.Vec3i;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FlightController
{
    public static void tick(Player player)
    {
        if (isInCreativeOrSpectator(player) ||  player.tickCount < 20) { // This is a hack to fix something that I don't understand
            return;
        }

        FlightMode allowed = AllowedFlightModes(player, false);
        switch (allowed) {
            case None, Elytra -> {
                if (player.getAbilities().mayfly) {
                    stopFlying(player);
                    // handle falling out of sky
                    if (!player.onGround()) { player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200)); }
                }
            }
            case Creative, Both -> {
                if (!player.getAbilities().mayfly) {
                    if (player.hasEffect(MobEffects.SLOW_FALLING)) {
                        player.removeEffect(MobEffects.SLOW_FALLING);
                    }
                    startFlying(player);
                }
            }
        }
    }

    public static boolean isInCreativeOrSpectator(Player player) {
        return player.isCreative() || player.isSpectator();
    }

    public static void startFlying(Player player) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
    }

    public static void stopFlying(Player player) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
    }

    public static FlightMode AllowedFlightModes(Player player, boolean onlyCareAboutElytra)
    {
        boolean hasAscended = AscendedRingCurio.HasAscendedRing(player);
        boolean CanElytraFly;
        boolean CanCreativeFly;

        if (hasAscended)
        {
            // fetch from config and look up allowed modes from truth table
            CanElytraFly = BalancedFlightConfig.ElytraAscended.get();
            CanCreativeFly = BalancedFlightConfig.CreativeAscended.get();
            FlightMode allowedModes = FlightMode.fromBools(CanElytraFly, CanCreativeFly);

            if (IsBeingDisrupted(player)) {
                return FlightMode.fromBools(CanElytraFly, false);
            }

            // if it's just creative, both, or neither, just return
            if (allowedModes != FlightMode.Elytra)
                return allowedModes;

            // if Elytra doesn't give unlimited creative flight,
            // check if Basic tier is allowed to fly.
            if (!CanCreativeFly && BalancedFlightConfig.CreativeAnchor.get())
            {
                if (IsWithinFlightRange(player))
                    return FlightMode.fromBools(CanElytraFly, true);
            }

            return allowedModes;
        }

        // only has basic ring at this point
        CanElytraFly = BalancedFlightConfig.ElytraAnchor.get();
        CanCreativeFly = BalancedFlightConfig.CreativeAnchor.get();

        if (IsBeingDisrupted(player)) {
            return FlightMode.fromBools(CanElytraFly, false);
        }

        if (onlyCareAboutElytra && !CanElytraFly)
            return FlightMode.None;

        if (IsWithinFlightRange(player))
            return FlightMode.fromBools(CanElytraFly, CanCreativeFly);
        else
            return FlightMode.None;
    }

    private static boolean IsWithinFlightRange(Player player)
    {
        if (player.level().dimension() != Level.OVERWORLD)
            return false;

        double anchorDistanceMultiplier = BalancedFlightConfig.anchorDistanceMultiplier.get();

        return FlightAnchorEntity.ActiveAnchors
                .entrySet()
                .stream()
                .anyMatch(anchor -> distSqr(anchor.getKey(), player.position()) < (anchorDistanceMultiplier * anchor.getValue().getSpeed()) * (anchorDistanceMultiplier * anchor.getValue().getSpeed()));
    }

    public static boolean IsBeingDisrupted(Player player)
    {
        if (player.level().dimension() != Level.OVERWORLD)
            return false;

        double disruptorsDistanceMultiplier = BalancedFlightConfig.disruptorDistanceMultiplier.get();

        return FlightDisruptorEntity.ActiveDisruptors
                .entrySet()
                .stream()
                .anyMatch(anchor -> distSqr(anchor.getKey(), player.position()) < (disruptorsDistanceMultiplier * (anchor.getValue().getSpeed() / 4 ) * (disruptorsDistanceMultiplier * anchor.getValue().getSpeed() / 4)));
    }

    private static double distSqr(Vec3i vec, Vec3 other) {
        double d1 = (double)vec.getX() - other.x;
        double d3 = (double)vec.getZ() - other.z;
        return d1 * d1 + d3 * d3;
    }

    public enum FlightMode {
        None,
        Elytra,
        Creative,
        Both;

        public static FlightMode fromBools(boolean ElytraAllowed, boolean CreativeAllowed) {
            if (ElytraAllowed && CreativeAllowed)
                return Both;

            if (ElytraAllowed)
                return Elytra;

            if (CreativeAllowed)
                return Creative;

            return None;
        }

        public boolean canElytraFly() {
            return this == Elytra || this == Both;
        }

        public boolean canCreativeFly() {
            return this == Creative || this == Both;
        }
    }
}
