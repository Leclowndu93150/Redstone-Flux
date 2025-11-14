package com.leclowndu93150.redstone_flux;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = RedstoneFlux.MODID)
public class TooltipHandler {
    
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        var tooltip = event.getToolTip();
        
        for (int i = 0; i < tooltip.size(); i++) {
            Component component = tooltip.get(i);
            String original = component.getString();
            String modified = EnergyTermReplacer.replaceEnergyTerms(original);
            
            if (!modified.equals(original)) {
                MutableComponent newComponent = Component.literal(modified).setStyle(component.getStyle());
                tooltip.set(i, newComponent);
            }
        }
    }
}
