package dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper;

import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GhostlyCreeperAttackGoal extends Goal {
    private final GhostlyCreeper creeper;
    private long lastCanUseCheck;
    private final int attackInterval = 20;
    
    public GhostlyCreeperAttackGoal(GhostlyCreeper pCreeper) {
        this.creeper = pCreeper;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }
    
    @Override
    public boolean canUse() {
        long gameTime = this.creeper.level().getGameTime();
        if (gameTime - this.lastCanUseCheck < this.attackInterval) {
            return false;
        }
        this.lastCanUseCheck = gameTime;
        LivingEntity target = this.creeper.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return this.getAttackReachSqr(target) >= this.creeper.distanceToSqr(target);
    }
    
    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.creeper.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        } else if (!this.creeper.getMoveControl().hasWanted() || !this.creeper.isAggressive()) {
            return false;
        }
        return EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
    }
    
    @Override
    public void start() {
        LivingEntity target = this.creeper.getTarget();
        if (target != null) {
            Vec3 pos = target.position();
            this.creeper.getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, 0.7F);
        }
        this.creeper.setAggressive(true);
    }
    
    @Override
    public void tick() {
        LivingEntity target = this.creeper.getTarget();
        if (target != null) {
            this.creeper.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }
    
    @Override
    public void stop() {
        LivingEntity livingEntity = this.creeper.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            this.creeper.setTarget(null);
        }
        this.creeper.setAggressive(false);
        this.creeper.getNavigation().stop();
    }
    
    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    
    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return this.creeper.getBbWidth() * 2.0F * this.creeper.getBbWidth() * 2.0F + attackTarget.getBbWidth();
    }
}
