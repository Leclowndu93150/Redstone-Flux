package com.leclowndu93150.redstone_flux.mixin;

import com.leclowndu93150.redstone_flux.EnergyTermReplacer;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UnitDisplayUtils.class, remap = false)
public class MekanismUnitDisplayUtilsMixin {
    
    @Inject(method = "getDisplayShort*", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplay(double value, UnitDisplayUtils.EnergyUnit unit, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);
        
        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }
}
