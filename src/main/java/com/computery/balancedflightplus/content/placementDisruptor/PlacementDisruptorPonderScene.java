package com.computery.balancedflightplus.content.placementDisruptor;

import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorBehaviour;
import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class PlacementDisruptorPonderScene {
    public PlacementDisruptorPonderScene() { }

    public static void ponderScene(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("flight_anchor", "Powered flight with Rotational Force");
        scene.configureBasePlate(1, 0, 5);

        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.world.showSection(util.select.layer(1), Direction.UP);
        scene.world.setKineticSpeed(util.select.everywhere(), 0.0F);

        Selection flightAnchorSelect = util.select.position(3, 1, 2);//
        scene.overlay.showText(90).placeNearTarget().text("The flight anchor requires an immense amount of power to operate.").pointAt(flightAnchorSelect.getCenter());
        scene.idle(100);

        BlockPos flightAnchorPos = util.grid.at(3, 1, 2);
        scene.world.modifyBlockEntity(flightAnchorPos, PlacementDisruptorEntity.class, entity -> {
            PlacementDisruptorBehaviour.beaconTick(entity.getLevel(), entity.getBlockPos(), entity);
        });

        scene.world.setKineticSpeed(util.select.everywhere(), 32.0F);
        scene.overlay.showText(90).placeNearTarget().text("For each RPM, you will be able to fly one block around the anchor.").pointAt(flightAnchorSelect.getCenter());
        scene.idle(100);

        scene.world.setKineticSpeed(util.select.everywhere(), 128.0F);
        scene.overlay.showText(1000).placeNearTarget().text("Higher speeds cover a much higher surface area.").pointAt(flightAnchorSelect.getCenter());
        scene.idle(100);

        scene.markAsFinished();
    }
}
