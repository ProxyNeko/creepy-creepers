package com.mcmoddev.creepycreepers.common.misc;

import com.mcmoddev.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CreeperSwellGoal extends Goal {

    private final GhostlyCreeperEntity creeper;
    private LivingEntity livingEntity;

    public CreeperSwellGoal(GhostlyCreeperEntity entitycreeper) {
        creeper = entitycreeper;
        setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = creeper.getAttackTarget();
        return creeper.getCreeperState() > 0 || livingentity != null && creeper.getDistanceSq(livingentity) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        creeper.getNavigator().clearPath();
        livingEntity = creeper.getAttackTarget();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        livingEntity = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (livingEntity == null) {
            creeper.setCreeperState(-1);
        } else {
            if (creeper.getDistanceSq(livingEntity) > 49.0D) {
                creeper.setCreeperState(-1);
            } else {
                if (!creeper.getEntitySenses().canSee(livingEntity)) {
                    creeper.setCreeperState(-1);
                } else {
                    creeper.setCreeperState(1);
                }
            }
        }
    }
}
