package dev.nertzhul.creepycreepers.entities;

import dev.nertzhul.creepycreepers.setup.CcSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GhostlyCreeper extends Creeper {
    public GhostlyCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }
    
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockUnderneath) { }
    
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
}
