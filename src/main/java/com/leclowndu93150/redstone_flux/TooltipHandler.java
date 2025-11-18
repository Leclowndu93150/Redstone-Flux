package com.leclowndu93150.redstone_flux;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneFlux.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
