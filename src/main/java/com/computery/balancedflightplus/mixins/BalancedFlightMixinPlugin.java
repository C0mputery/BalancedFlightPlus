package com.computery.balancedflightplus.mixins;

import com.computery.balancedflightplus.BalancedFlight;
import com.computery.balancedflightplus.foundation.config.BalancedFlightConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class BalancedFlightMixinPlugin implements IMixinConfigPlugin
{
    private static final String MIXIN_PACKAGE_ROOT = "com.computery.balancedflightplus.mixins.";

    @Override
    public void onLoad(String mixinPackage) {
       BalancedFlightConfig.init();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_ROOT)) {
            BalancedFlight.LOGGER.error("Expected mixin '{}' to start with package root '{}', treating as foreign and " +
                    "disabling!", mixinClassName, MIXIN_PACKAGE_ROOT);

            return false;
        }

        if (!BalancedFlightConfig.ConfigSpec.isLoaded())
        {
            BalancedFlight.LOGGER.error("Trying to use mixin plugin without config loaded");
        }

        String mixin = mixinClassName.substring(MIXIN_PACKAGE_ROOT.length());

        if (mixin.equals("ElytraMixin") || mixin.equals("ElytraServerMixin")|| mixin.equals("ElytraUpdateMixin")) {
            return BalancedFlightConfig.ElytraAnchor.get() || BalancedFlightConfig.ElytraAscended.get();
        }

        if (mixin.equals("ElytraRocketShiftKeyMixin")) {
            return BalancedFlightConfig.infiniteRockets.get();
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins()
    {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}