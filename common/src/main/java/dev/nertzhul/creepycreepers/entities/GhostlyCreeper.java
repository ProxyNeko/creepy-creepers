package dev.nertzhul.creepycreepers.entities;

import dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper.GhostlyCreeperAttackGoal;
import dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper.GhostlyCreeperAvoidEntityGoal;
import dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper.GhostlyCreeperRandomStroll;
import dev.nertzhul.creepycreepers.setup.CcSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GhostlyCreeper extends Creeper {
    public GhostlyCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new GhostlyCreeperMoveControl(this);
        this.setNoGravity(true);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }
    
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.07D)
            .add(Attributes.FLYING_SPEED, 0.07D);
    }
    
    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SwellGoal(this));
        this.goalSelector.addGoal(2, new GhostlyCreeperAvoidEntityGoal<>(this, Ocelot.class));
        this.goalSelector.addGoal(2, new GhostlyCreeperAvoidEntityGoal<>(this, Cat.class));
        this.goalSelector.addGoal(4, new GhostlyCreeperAttackGoal(this));
        this.goalSelector.addGoal(5, new GhostlyCreeperRandomStroll(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }
    
    @Override
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }
            
            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(CcSoundEvents.GHOSTLY_CREEPER_SCREAM.get(), 1.0F, 1.0F);
            }
            
            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }
            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }
        super.tick();
    }
    
    @Override @NotNull
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockUnderneath) { }
    
    private static class GhostlyCreeperMoveControl extends MoveControl {
        private final GhostlyCreeper creeper;
        
        public GhostlyCreeperMoveControl(GhostlyCreeper pCreeper) {
            super(pCreeper);
            this.creeper = pCreeper;
        }
        
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - this.creeper.getX(), this.wantedY - this.creeper.getY(), this.wantedZ - this.creeper.getZ());
                double d = vec3.length();
                if (d < this.creeper.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    this.creeper.setDeltaMovement(this.creeper.getDeltaMovement().scale(0.5));
                } else {
                    this.creeper.setDeltaMovement(this.creeper.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d)));
                    if (this.creeper.getTarget() == null) {
                        Vec3 vec32 = this.creeper.getDeltaMovement();
                        this.creeper.setYRot(-((float) Mth.atan2(vec32.x, vec32.z)) * 57.3F);
                    } else {
                        double e = this.creeper.getTarget().getX() - this.creeper.getX();
                        double f = this.creeper.getTarget().getZ() - this.creeper.getZ();
                        this.creeper.setYRot(-((float) Mth.atan2(e, f)) * 57.295776F);
                    }
                    this.creeper.yBodyRot = this.creeper.getYRot();
                }
            }
            this.creeper.resetFallDistance();
        }
    }
}
