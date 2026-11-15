package dev.livingtown;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LivingTownMod implements ModInitializer {
    public static final String MOD_ID = "livingtown";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("livingtown")
                .then(Commands.literal("help").executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal(
                        "LivingTown is installed. NPC spawning and schedule commands are being built. " +
                        "The first release will add /livingtown spawn, /livingtown list and editable routines."
                    ), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("version").executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal("LivingTown 0.1.0 — Minecraft 1.21.11"), false);
                    return Command.SINGLE_SUCCESS;
                }))
            )
        );

        LOGGER.info("LivingTown initialized for Minecraft 1.21.11");
    }
}
