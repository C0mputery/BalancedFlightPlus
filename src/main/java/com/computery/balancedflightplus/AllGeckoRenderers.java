package com.computery.balancedflightplus;

import com.computery.balancedflightplus.content.flightAnchor.entity.FlightAnchorEntity;
import com.computery.balancedflightplus.content.flightAnchor.FlightAnchorItem;
import com.computery.balancedflightplus.content.flightDisruptor.FlightDisruptorItem;
import com.computery.balancedflightplus.content.flightDisruptor.entity.FlightDisruptorEntity;
import com.computery.balancedflightplus.content.flightDisruptor.render.FlightDisruptorBeamRenderer;
import com.computery.balancedflightplus.content.flightDisruptor.render.FlightDisruptorSafeRenderer;
import com.computery.balancedflightplus.content.placementDisruptor.PlacementDisruptorItem;
import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity;
import com.computery.balancedflightplus.content.placementDisruptor.render.PlacementDisruptorBeamRenderer;
import com.computery.balancedflightplus.content.placementDisruptor.render.PlacementDisruptorSafeRenderer;
import com.computery.balancedflightplus.foundation.render.ConfiguredGeoModel;
import com.computery.balancedflightplus.foundation.render.KineticGeckoRenderInfo;
import com.computery.balancedflightplus.content.flightAnchor.render.*;

public class AllGeckoRenderers
{
    public static KineticGeckoRenderInfo<FlightAnchorEntity, ?> FlightAnchorGeckoRenderer =
            new KineticGeckoRenderInfo<FlightAnchorEntity, FlightAnchorItem>(
                    new ConfiguredGeoModel("flight_anchor"),
                    new FlightAnchorSafeRenderer(),
                    BalancedFlight.FLIGHT_ANCHOR_BLOCK.get().defaultBlockState(),
                    new FlightAnchorBeamRenderer());
    public static KineticGeckoRenderInfo<FlightDisruptorEntity, ?> FlightDisruptorGeckoRenderer =
            new KineticGeckoRenderInfo<FlightDisruptorEntity, FlightDisruptorItem>(
                    new ConfiguredGeoModel("flight_disruptor"),
                    new FlightDisruptorSafeRenderer(),
                    BalancedFlight.FLIGHT_DISRUPTOR_BLOCK.get().defaultBlockState(),
                    new FlightDisruptorBeamRenderer());

    public static KineticGeckoRenderInfo<PlacementDisruptorEntity, ?> PlacementDisruptorGeckoRenderer =
            new KineticGeckoRenderInfo<PlacementDisruptorEntity, PlacementDisruptorItem>(
                    new ConfiguredGeoModel("placement_disruptor"),
                    new PlacementDisruptorSafeRenderer(),
                    BalancedFlight.PLACEMENT_DISRUPTOR_BLOCK.get().defaultBlockState(),
                    new PlacementDisruptorBeamRenderer());
}


