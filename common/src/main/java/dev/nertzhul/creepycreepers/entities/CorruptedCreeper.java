package dev.nertzhul.creepycreepers.entities;

import dev.nertzhul.creepycreepers.util.ExplosionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CorruptedCreeper extends Creeper {
    private int targetChangeTime;
    
    public CorruptedCreeper(EntityType<? extends CorruptedCreeper> entityType, Level level) {
        super(entityType, level);
    }
    
    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.MAX_HEALTH, 40.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D)
            .add(Attributes.ARMOR, 0.8D);
    }
    
    @Override
    protected void customServerAiStep() {
        if (this.level().isDay() && this.tickCount >= this.targetChangeTime + 600) {
            float f = this.getLightLevelDependentMagicValue();
            if (f > 0.5F && this.level().canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setTarget(null);
                this.teleport();
            }
        }
        super.customServerAiStep();
    }
    
    @Override
    protected void explodeCreeper() {
        if (!this.level().isClientSide) {
            float f = this.explosionRadius * (this.isPowered() ? 2.0F : 1.0F);
            this.dead = true;
            
            ExplosionUtil.fakeExplosion((ServerLevel) level(), this, this.getX(), this.getY(), this.getZ(), f, true);
            List<LivingEntity> affected = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5), entity -> this.distanceToSqr(entity) <= 5 * 5);
            
            for (LivingEntity entity : affected) {
                Vec3 v = entity.position();
                entity.randomTeleport(
                    v.x + (this.random.nextDouble() - 0.5) * 64.0,
                    v.y + (double) (this.random.nextInt(64) - 32),
                    v.z + (this.random.nextDouble() - 0.5) * 64.0,
                    true
                );
            }
            
            this.discard();
            this.spawnLingeringCloud();
        }
    }
    
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.targetChangeTime = 0;
        } else {
            this.targetChangeTime = this.tickCount;
        }
    }
    
    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double e = this.getY() + (double) (this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            return this.teleport(d, e, f);
        } else {
            return false;
        }
    }
    
    private boolean teleport(double x, double y, double z) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);
        
        while (mutableBlockPos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(mutableBlockPos).blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }
        
        BlockState blockState = this.level().getBlockState(mutableBlockPos);
        boolean bl = blockState.blocksMotion();
        boolean bl2 = blockState.getFluidState().is(FluidTags.WATER);
        
        if (bl && !bl2) {
            Vec3 vec3 = this.position();
            boolean bl3 = this.randomTeleport(x, y, z, true);
            if (bl3) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
                if (!this.isSilent()) {
                    this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 0.4F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 0.4F);
                }
            }
            return bl3;
        } else {
            return false;
        }
    }
    
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 46) {
            for (int j = 0; j < 128; ++j) {
                double d = (double) j / 127.0;
                
                float f = (this.random.nextFloat() - 0.5F) * 0.2F;
                float g = (this.random.nextFloat() - 0.5F) * 0.2F;
                float h = (this.random.nextFloat() - 0.5F) * 0.2F;
                
                double e = Mth.lerp(d, this.xo, this.getX()) + (this.random.nextDouble() - 0.5) * (double) this.getBbWidth() * 2.0;
                double k = Mth.lerp(d, this.yo, this.getY()) + this.random.nextDouble() * (double) this.getBbHeight();
                double l = Mth.lerp(d, this.zo, this.getZ()) + (this.random.nextDouble() - 0.5) * (double) this.getBbWidth() * 2.0;
                
                DustParticleOptions particle = new DustParticleOptions(new Vector3f(0, 100 / 255F, 0), 0.8F);
                this.level().addParticle(particle, e, k, l, f, g, h);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }
}
