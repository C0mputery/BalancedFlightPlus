package com.computery.balancedflightplus.content.flightDisruptor;

import com.computery.balancedflightplus.AllGeckoRenderers;
import com.computery.balancedflightplus.foundation.render.AnimatedBlockItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FlightDisruptorItem extends AnimatedBlockItem<FlightDisruptorItem>
{
    public FlightDisruptorItem(Block block, Properties props) { super(block, props, () -> AllGeckoRenderers.FlightDisruptorGeckoRenderer.ItemRenderer); }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag p_41424_)
    {
        super.appendHoverText(stack, world, tooltip, p_41424_);

        tooltip.add(Component.literal("Stops flight around it based on how much RPM is powering it.").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.literal("Only works in the overworld? Idk lol probly").withStyle(ChatFormatting.RED));
    }
}
