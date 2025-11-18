package com.leclowndu93150.redstone_flux.mixin;

import com.leclowndu93150.redstone_flux.EnergyTermReplacer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UnitDisplayUtils.class, remap = false)
public class MekanismUnitDisplayUtilsMixin {

    @Inject(method = "getDisplayShort(Lmekanism/api/math/FloatingLong;Lmekanism/common/util/UnitDisplayUtils$EnergyUnit;)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplayFloatingLong(FloatingLong value, UnitDisplayUtils.EnergyUnit unit, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);

        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }

    @Inject(method = "getDisplayShort(DLmekanism/common/util/UnitDisplayUtils$TemperatureUnit;)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplayTemperature(double value, UnitDisplayUtils.TemperatureUnit unit, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);

        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }

    @Inject(method = "getDisplayShort(DLmekanism/common/util/UnitDisplayUtils$TemperatureUnit;Z)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplayTemperatureShift(double value, UnitDisplayUtils.TemperatureUnit unit, boolean shift, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);

        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }

    @Inject(method = "getDisplayShort(DLmekanism/common/util/UnitDisplayUtils$TemperatureUnit;ZI)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplayTemperatureShiftDecimal(double value, UnitDisplayUtils.TemperatureUnit unit, boolean shift, int decimalPlaces, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);

        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }

    @Inject(method = "getDisplayShort(DLmekanism/common/util/UnitDisplayUtils$RadiationUnit;I)Lnet/minecraft/network/chat/Component;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void replaceEnergyDisplayRadiation(double value, UnitDisplayUtils.RadiationUnit unit, int decimalPlaces, CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        String originalText = original.getString();
        String modified = EnergyTermReplacer.replaceEnergyTerms(originalText);

        if (!modified.equals(originalText)) {
            cir.setReturnValue(Component.literal(modified).setStyle(original.getStyle()));
        }
    }
}