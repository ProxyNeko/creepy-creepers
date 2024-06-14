package dev.nertzhul.creepycreepers.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

public class TuffCreeper extends Creeper {
    private final SoundType soundType = SoundType.TUFF;
    
    public TuffCreeper(EntityType<? extends TuffCreeper> entityType, Level level) {
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
    public void explodeCreeper() {
        if (!this.level().isClientSide) {
            float f = this.isPowered() ? 3.0F : 1.5F;
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, Level.ExplosionInteraction.MOB);
            this.discard();
            this.spawnLingeringCloud();
        }
    }
    
    public static boolean checkCreeperSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (pos.getY() < level.getSeaLevel() - 5) {
            return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
        }
        return false;
    }
    
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(this.soundType.getStepSound(), this.soundType.getVolume() * 0.15F, this.soundType.getPitch());
    }
}
