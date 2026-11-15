package dev.livingtown.entity;

import dev.livingtown.LivingTownMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class LivingTownEntities {
    public static final ResourceKey<EntityType<?>> NPC_KEY = ResourceKey.create(
        Registries.ENTITY_TYPE,
        ResourceLocation.fromNamespaceAndPath(LivingTownMod.MOD_ID, "npc")
    );

    public static final EntityType<LivingTownNpc> NPC = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        NPC_KEY,
        EntityType.Builder.of(LivingTownNpc::new, MobCategory.CREATURE)
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8)
            .build(NPC_KEY)
    );

    private LivingTownEntities() {}

    public static void register() {
        FabricDefaultAttributeRegistry.register(NPC, LivingTownNpc.createAttributes());
        LivingTownMod.LOGGER.info("Registered LivingTown NPC entity");
    }
}
