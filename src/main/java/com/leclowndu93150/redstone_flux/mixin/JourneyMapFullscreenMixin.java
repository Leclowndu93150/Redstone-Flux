package com.leclowndu93150.redstone_flux.mixin;

import journeymap.client.ui.fullscreen.Fullscreen;
import journeymap.client.ui.fullscreen.MapChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Fullscreen.class, remap = false)
public abstract class JourneyMapFullscreenMixin {

    @Shadow
    private MapChat chat;

    @Inject(method = "m_5534_", at = @At("HEAD"), cancellable = true)
    private void redstoneFlux$preventChatNPE(char typedChar, int keyCode, CallbackInfoReturnable<Boolean> cir) {
        if (this.chat == null) {
            cir.setReturnValue(false);
        }
    }
}
