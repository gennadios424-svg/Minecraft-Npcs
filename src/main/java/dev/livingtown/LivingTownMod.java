package dev.livingtown;

import dev.livingtown.command.LivingTownCommands;
import dev.livingtown.entity.LivingTownEntities;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LivingTownMod implements ModInitializer {
    public static final String MOD_ID = "livingtown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LivingTownEntities.register();
        LivingTownCommands.register();
        LOGGER.info("LivingTown initialized for Minecraft 1.21.11");
    }
}
