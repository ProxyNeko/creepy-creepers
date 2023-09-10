package dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper;

import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GhostlyCreeperAvoidEntityGoal<T extends LivingEntity> extends Goal {
    protected final GhostlyCreeper creeper;
    private final double walkSpeedModifier = 1.0D;
    private final double sprintSpeedModifier = 1.2D;
    private final float maxDist = 6.0F;
    
    protected Vec3 wanted;
    protected T toAvoid;
    protected final Class<T> avoidClass;
    private final TargetingConditions avoidEntityTargeting;
    
    public GhostlyCreeperAvoidEntityGoal(GhostlyCreeper pCreeper, Class<T> pToAvoid) {
        this.creeper = pCreeper;
        this.avoidClass = pToAvoid;
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(this.maxDist).selector(EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }
    
    @Override
    public boolean canUse() {
        this.toAvoid = this.creeper.level()
            .getNearestEntity(
                this.creeper.level().getEntitiesOfClass(this.avoidClass, this.creeper.getBoundingBox().inflate(this.maxDist, 4.0D, this.maxDist)),
                this.avoidEntityTargeting, this.creeper, this.creeper.getX(), this.creeper.getY(), this.creeper.getZ()
            );
        if (this.toAvoid == null) { return false; }
        
        Vec3 view = this.creeper.getViewVector(0.0F);
        Vec3 vec3 = AirAndWaterRandomPos.getPos(this.creeper, 8, 8, 4, view.x, view.z, 1.5F);
        
        if (this.toAvoid.distanceToSqr(vec3) < this.toAvoid.distanceToSqr(this.creeper)) {
            return false;
        } else if (vec3 == null) {
            vec3 = AirAndWaterRandomPos.getPos(this.creeper, 16, 8, 8, view.x, view.z, 2.0F);
            if (vec3 == null) {
                return false;
            }
        }
        
        Path path = this.creeper.getNavigation().createPath(BlockPos.containing(vec3), 0);
        if (path != null && !path.canReach()) { return false; }
        
        this.wanted = vec3;
        return true;
    }
    
    @Override
    public boolean canContinueToUse() {
        return this.creeper.distanceToSqr(this.toAvoid) < 64.0D;
    }
    
    @Override
    public void start() {
        this.creeper.getMoveControl().setWantedPosition(this.wanted.x, this.wanted.y, this.wanted.z, this.walkSpeedModifier);
    }
    
    @Override
    public void stop() {
        this.toAvoid = null;
    }
    
    @Override
    public void tick() {
        this.creeper.getLookControl().setLookAt(this.wanted);
        if (this.creeper.distanceToSqr(this.toAvoid) < 49.0D) {
            this.creeper.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.creeper.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }
}
