package com.leclowndu93150.redstone_flux.mixin;

import com.leclowndu93150.redstone_flux.EnergyTermReplacer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import snownee.jade.impl.ui.ProgressStyle;

@Mixin(value = ProgressStyle.class, remap = false)
public class SimpleProgressStyleMixin {
    
    @ModifyVariable(
        method = "render",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0
    )
    private Component replaceEnergyInProgressText(Component text) {
        if (text != null) {
            String original = text.getString();
            String modified = EnergyTermReplacer.replaceEnergyTerms(original);
            
            if (!modified.equals(original)) {
                return Component.literal(modified).setStyle(text.getStyle());
            }
        }
        return text;
    }
}
