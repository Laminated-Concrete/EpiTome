package net.fryke.epitome.helpers;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fryke.epitome.EpiTomeMod;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger(EpiTomeMod.MOD_ID);

    public static void log(String text) {
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            LOGGER.info("Server Env Log : " + text);
        } else {
            LOGGER.info("Client Env Log : " + text);
        }
    }

}

