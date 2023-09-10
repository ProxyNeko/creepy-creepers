package dev.nertzhul.creepycreepers.entities.ai.ghostlycreeper;

import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GhostlyCreeperRandomStroll extends Goal {
    private final GhostlyCreeper creeper;
    private final RandomSource random;
    
    public GhostlyCreeperRandomStroll(GhostlyCreeper pCreeper) {
        this.creeper = pCreeper;
        this.random = this.creeper.getRandom();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }
    
    @Override
    public boolean canUse() {
        return !this.creeper.getMoveControl().hasWanted() && this.random.nextFloat() < 0.1F;
    }
    
    @Override
    public boolean canContinueToUse() {
        return false;
    }
    
    @Override
    public void tick() {
        for (int i = 0; i < 3; ++i) {
            BlockPos possible = this.creeper.blockPosition().offset(
                this.random.nextInt(15) - 7,
                this.random.nextInt(15) - 8,
                this.random.nextInt(15) - 7
            );
            
            if (this.creeper.level().isEmptyBlock(possible)) {
                this.creeper.getMoveControl().setWantedPosition(
                    (double) possible.getX() + 0.5,
                    (double) possible.getY() + 0.5,
                    (double) possible.getZ() + 0.5, 0.25
                );
                
                if (this.creeper.getTarget() == null) {
                    this.creeper.getLookControl().setLookAt(
                        (double) possible.getX() + 0.5,
                        (double) possible.getY() + 0.5,
                        (double) possible.getZ() + 0.5,
                        180.0F, 20.0F
                    );
                }
                break;
            }
        }
    }
}
