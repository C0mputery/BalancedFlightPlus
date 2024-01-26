package com.computery.balancedflightplus.content.placementDisruptor.render;

import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PlacementDisruptorBeamRenderer implements BlockEntityRenderer<PlacementDisruptorEntity>
{

    @Override
    public void render(PlacementDisruptorEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
    {
        pPoseStack.pushPose();
        pPoseStack.translate(-0.5D, 0, -0.5D);

        List<BeaconBlockEntity.BeaconBeamSection> list = pBlockEntity.getBeamSections();
        int j = 0;

        for(int k = 0; k < list.size(); ++k) {
            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            BeaconRenderer.renderBeaconBeam(pPoseStack, pBufferSource, AnimationTickHolder.getPartialTicks(pBlockEntity.getLevel()), AnimationTickHolder.getTicks(pBlockEntity.getLevel()), j, k == list.size() - 1 ? 1024 : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
            j += beaconblockentity$beaconbeamsection.getHeight();
        }


        pPoseStack.popPose();
    }


    public boolean shouldRenderOffScreen(PlacementDisruptorEntity pBlockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 256;
    }

    public boolean shouldRender(PlacementDisruptorEntity pBlockEntity, Vec3 pCameraPos) {
        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(pCameraPos.multiply(1.0D, 0.0D, 1.0D), this.getViewDistance());
    }
}
