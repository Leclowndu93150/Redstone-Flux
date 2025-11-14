package com.leclowndu93150.redstone_flux.mixin;

import com.leclowndu93150.redstone_flux.EnergyTermReplacer;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Consumer;

@Mixin(TranslatableContents.class)
public class TranslatableContentsMixin {
    
    @ModifyVariable(
        method = "decomposeTemplate",
        at = @At("HEAD"),
        argsOnly = true,
        index = 1
    )
    private String replaceEnergyTermsInTemplate(String formatTemplate) {
        return EnergyTermReplacer.replaceEnergyTerms(formatTemplate);
    }
}
