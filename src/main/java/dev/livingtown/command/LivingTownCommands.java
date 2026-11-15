package dev.livingtown.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.livingtown.entity.LivingTownEntities;
import dev.livingtown.entity.LivingTownNpc;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class LivingTownCommands {
    private LivingTownCommands() {}

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("livingtown")
                .then(Commands.literal("help").executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal(
                        "LivingTown commands: /livingtown spawn <name>, /livingtown list, " +
                        "/livingtown remove <name>, /livingtown goto <name> <x> <y> <z>"
                    ), false);
                    return 1;
                }))
                .then(Commands.literal("spawn")
                    .then(Commands.argument("name", StringArgumentType.word()).executes(context -> {
                        var source = context.getSource();
                        ServerLevel level = source.getLevel();
                        LivingTownNpc npc = LivingTownEntities.NPC.create(level, EntitySpawnReason.COMMAND);
                        if (npc == null) {
                            source.sendFailure(Component.literal("Could not create an NPC."));
                            return 0;
                        }
                        Vec3 pos = source.getPosition();
                        npc.moveTo(pos.x, pos.y, pos.z, source.getRotation().y, 0.0F);
                        npc.setNpcName(StringArgumentType.getString(context, "name"));
                        if (!level.addFreshEntity(npc)) {
                            source.sendFailure(Component.literal("Could not add the NPC to this world."));
                            return 0;
                        }
                        source.sendSuccess(() -> Component.literal("Spawned " + npc.getNpcName() + "."), true);
                        return 1;
                    }))
                )
                .then(Commands.literal("list").executes(context -> {
                    var source = context.getSource();
                    AABB area = AABB.ofSize(source.getPosition(), 256.0D, 256.0D, 256.0D);
                    var npcs = source.getLevel().getEntitiesOfClass(LivingTownNpc.class, area);
                    if (npcs.isEmpty()) {
                        source.sendSuccess(() -> Component.literal("No LivingTown NPCs found within 128 blocks."), false);
                        return 0;
                    }
                    String names = npcs.stream().map(LivingTownNpc::getNpcName).sorted().reduce((a, b) -> a + ", " + b).orElse("");
                    source.sendSuccess(() -> Component.literal("Nearby NPCs (" + npcs.size() + "): " + names), false);
                    return npcs.size();
                }))
                .then(Commands.literal("remove")
                    .then(Commands.argument("name", StringArgumentType.word()).executes(context -> {
                        var source = context.getSource();
                        String name = StringArgumentType.getString(context, "name");
                        AABB area = AABB.ofSize(source.getPosition(), 256.0D, 256.0D, 256.0D);
                        var target = source.getLevel().getEntitiesOfClass(LivingTownNpc.class, area).stream()
                            .filter(npc -> npc.getNpcName().equalsIgnoreCase(name)).findFirst();
                        if (target.isEmpty()) {
                            source.sendFailure(Component.literal("No nearby NPC named " + name + " was found."));
                            return 0;
                        }
                        target.get().discard();
                        source.sendSuccess(() -> Component.literal("Removed " + name + "."), true);
                        return 1;
                    }))
                )
                .then(Commands.literal("goto")
                    .then(Commands.argument("name", StringArgumentType.word())
                        .then(Commands.argument("x", DoubleArgumentType.doubleArg())
                            .then(Commands.argument("y", DoubleArgumentType.doubleArg())
                                .then(Commands.argument("z", DoubleArgumentType.doubleArg()).executes(context -> {
                                    var source = context.getSource();
                                    String name = StringArgumentType.getString(context, "name");
                                    AABB area = AABB.ofSize(source.getPosition(), 256.0D, 256.0D, 256.0D);
                                    var target = source.getLevel().getEntitiesOfClass(LivingTownNpc.class, area).stream()
                                        .filter(npc -> npc.getNpcName().equalsIgnoreCase(name)).findFirst();
                                    if (target.isEmpty()) {
                                        source.sendFailure(Component.literal("No nearby NPC named " + name + " was found."));
                                        return 0;
                                    }
                                    BlockPos destination = BlockPos.containing(
                                        DoubleArgumentType.getDouble(context, "x"),
                                        DoubleArgumentType.getDouble(context, "y"),
                                        DoubleArgumentType.getDouble(context, "z")
                                    );
                                    if (!target.get().goTo(destination)) {
                                        source.sendFailure(Component.literal("That destination is not loaded or could not be reached."));
                                        return 0;
                                    }
                                    source.sendSuccess(() -> Component.literal(name + " is walking to " + destination.toShortString() + "."), true);
                                    return 1;
                                }))))
                        )
                    )
                )
            )
        );
    }
}
