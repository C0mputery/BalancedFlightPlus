package com.computery.balancedflightplus.content.placementDisruptor;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import com.computery.balancedflightplus.BalancedFlight;
import com.computery.balancedflightplus.content.placementDisruptor.entity.PlacementDisruptorEntity;
import com.computery.balancedflightplus.foundation.RegistrateExtensions;
import lombok.experimental.ExtensionMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@ExtensionMethod({ RegistrateExtensions.class})
@Mod.EventBusSubscriber(modid = BalancedFlight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlacementDisruptorBlock extends HorizontalKineticBlock implements IBE<PlacementDisruptorEntity>, BeaconBeamBlock, IRotate
{
    public PlacementDisruptorBlock(Properties props) { super(props); }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING).getClockWise() || face == state.getValue(HORIZONTAL_FACING).getClockWise().getOpposite();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown())
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());

        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
    }

    public @NotNull DyeColor getColor() {
        return DyeColor.RED;
    }

    @Override public Class<PlacementDisruptorEntity> getBlockEntityClass() { return PlacementDisruptorEntity.class; }
    @Override public BlockEntityType<? extends PlacementDisruptorEntity> getBlockEntityType() { return BalancedFlight.PLACEMENT_DISRUPTOR_BLOCK_ENTITY.get(); }

    @Override public @NotNull RenderShape getRenderShape(@NotNull BlockState state) { return RenderShape.ENTITYBLOCK_ANIMATED; }
    @Override public Direction.Axis getRotationAxis(BlockState state) { return state.getValue(HORIZONTAL_FACING).getAxis();}
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }
}

