package com.computery.balancedflightplus.content.placementDisruptor.entity;

import com.google.common.collect.Lists;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementDisruptorEntity extends KineticBlockEntity implements GeoBlockEntity
{
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public static Map<BlockPos, com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity> ActiveDisruptors = new HashMap<>();

    List<BeaconBlockEntity.BeaconBeamSection> beamSections = Lists.newArrayList();
    List<BeaconBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.newArrayList();
    int lastCheckY;
    boolean isActive;
    public float placedRenderTime;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ANIMATION = RawAnimation.begin().then("animation.placement_disruptor.deploy", Animation.LoopType.PLAY_ONCE);


    public PlacementDisruptorEntity(BlockEntityType type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }


    public boolean shouldPlayAnimsWhileGamePaused() {
        return true;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list)
    {
        list.add(new PlacementDisruptorBehaviour(this));
    }


    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, (state) -> state.setAndContinue(ANIMATION)));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object entity) {
        return RenderUtils.getCurrentTick();
    }

    public List<BeaconBlockEntity.BeaconBeamSection> getBeamSections() {
        return this.beamSections;
    }
}
