package dev.nertzhul.creepycreepers.entities;

import dev.nertzhul.creepycreepers.util.ExplosionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SnowyCreeper extends Creeper {
    private final SoundType soundType = SoundType.SNOW;
    
    public SnowyCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }
    
    public static boolean checkCreeperSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (pos.getY() > level.getSeaLevel()) {
            return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
        }
        return false;
    }
    
    @Override
    protected void explodeCreeper() {
        if (!this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            this.dead = true;
            
            int radius = this.isPowered() ? 9 : 5;
            ServerLevel level = (ServerLevel) this.level();
            AABB affectedArea = this.getBoundingBox().inflate(radius);
            
            ExplosionUtil.fakeExplosion(level, this, this.getX(), this.getY(), this.getZ(), (radius / 2.0F), false);
            BlockPos.betweenClosedStream(affectedArea)
                .filter(blockPos -> this.distanceToSqr(Vec3.atCenterOf(blockPos)) < radius * radius)
                .filter(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    return state.is(BlockTags.REPLACEABLE) && state.getFluidState().isEmpty()
                            && Blocks.SNOW.defaultBlockState().canSurvive(level, blockPos);
                })
                .forEach(blockPos -> {
                    level.setBlockAndUpdate(blockPos, Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, this.random.nextInt(1, 4)));
                    level.sendParticles(ParticleTypes.SNOWFLAKE, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 10,
                        (this.random.nextDouble() - 0.5D), (this.random.nextDouble() - 0.5D), (this.random.nextDouble() - 0.5D), (this.random.nextDouble() - 0.5D) * 0.5D);
                });
            
            level.getEntitiesOfClass(LivingEntity.class, affectedArea, entity -> this.distanceToSqr(entity) < radius * radius)
                .forEach(living -> {
                    living.knockback(1.2D, this.getX() - living.getX(), this.getZ() - living.getZ());
                    living.setTicksFrozen((int) (living.getTicksRequiredToFreeze() * 0.9F));
                });
            
            this.discard();
        }
    }
    
    @Override
    public void aiStep() {
        super.aiStep();
        
        if (this.level().isClientSide) {
            int amount = this.random.nextInt(3, 5);
            for (int i = 0; i < amount; ++i) {
                if (this.random.nextFloat() < 0.97) { continue; }
                this.level().addParticle(ParticleTypes.SNOWFLAKE,
                    this.getRandomX(0.8D), this.getRandomY() - 0.05D, this.getRandomZ(0.8D),
                    (this.random.nextDouble() - 0.5D) * 0.05D, (this.random.nextDouble() - 0.5D) * 0.01D, (this.random.nextDouble() - 0.5D) * 0.05D
                );
            }
        }
    }
    
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(this.soundType.getStepSound(), this.soundType.getVolume() * 0.15F, this.soundType.getPitch());
    }
    
    @Override
    protected void playMuffledStepSound(BlockState state) {
        this.playSound(this.soundType.getStepSound(), this.soundType.getVolume() * 0.05F, this.soundType.getPitch() * 0.8F);
    }
}
