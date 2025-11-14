package com.leclowndu93150.redstone_flux;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(RedstoneFlux.MODID)
public class RedstoneFlux {
    public static final String MODID = "redstone_flux";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RedstoneFlux(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Redstone Flux initialized");
    }
}
