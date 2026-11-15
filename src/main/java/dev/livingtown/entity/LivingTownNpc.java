package dev.livingtown.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
        goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return scriptedDestination != null;
            }

            @Override
            public boolean canContinueToUse() {
                return scriptedDestination != null && !getNavigation().isDone();
            }

            @Override
            public void start() {
                if (scriptedDestination != null) {
                    getNavigation().moveTo(
                        scriptedDestination.getX() + 0.5D,
                        scriptedDestination.getY(),
                        scriptedDestination.getZ() + 0.5D,
                        1.0D
                    );
                }
            }

            @Override
            public void tick() {
                if (getNavigation().isDone()) {
                    scriptedDestination = null;
                }
            }

            @Override
            public void stop() {
                if (getNavigation().isDone()) {
                    scriptedDestination = null;
                }
            }
        });
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
        if (destination == null || !level().hasChunkAt(destination)) {
            return false;
        }
        scriptedDestination = destination.immutable();
        return true;
    }

    public BlockPos getScriptedDestination() {
        return scriptedDestination;
    }
}
