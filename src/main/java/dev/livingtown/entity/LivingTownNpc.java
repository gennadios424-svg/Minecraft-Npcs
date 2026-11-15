package dev.livingtown.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public final class LivingTownNpc extends PathfinderMob {
    private BlockPos scriptedDestination;

    public LivingTownNpc(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.25D)
            .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public void setNpcName(String name) {
        setCustomName(net.minecraft.network.chat.Component.literal(name));
        setCustomNameVisible(true);
    }

    public String getNpcName() {
        return getCustomName() == null ? "NPC" : getCustomName().getString();
    }

    public boolean goTo(BlockPos destination) {
        if (destination == null || !level().isLoaded(destination)) {
            return false;
        }
        scriptedDestination = destination.immutable();
        return getNavigation().moveTo(
            destination.getX() + 0.5D,
            destination.getY(),
            destination.getZ() + 0.5D,
            1.0D
        );
    }

    public BlockPos getScriptedDestination() {
        return scriptedDestination;
    }
}
