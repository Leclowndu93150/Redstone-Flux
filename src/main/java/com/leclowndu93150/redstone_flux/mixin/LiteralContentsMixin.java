package com.leclowndu93150.redstone_flux.mixin;

import com.leclowndu93150.redstone_flux.EnergyTermReplacer;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LiteralContents.class)
public class LiteralContentsMixin {
    
    @Inject(method = "visit(Lnet/minecraft/network/chat/FormattedText$ContentConsumer;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void replaceInVisit(FormattedText.ContentConsumer<?> consumer, CallbackInfoReturnable<Optional<?>> cir) {
        LiteralContents self = (LiteralContents) (Object) this;
        String original = self.text();
        String modified = EnergyTermReplacer.replaceEnergyTerms(original);
        
        if (!modified.equals(original)) {
            cir.setReturnValue(consumer.accept(modified));
        }
    }
    
    @Inject(method = "visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void replaceInStyledVisit(FormattedText.StyledContentConsumer<?> consumer, Style style, CallbackInfoReturnable<Optional<?>> cir) {
        LiteralContents self = (LiteralContents) (Object) this;
        String original = self.text();
        String modified = EnergyTermReplacer.replaceEnergyTerms(original);
        
        if (!modified.equals(original)) {
            cir.setReturnValue(consumer.accept(style, modified));
        }
    }
}
