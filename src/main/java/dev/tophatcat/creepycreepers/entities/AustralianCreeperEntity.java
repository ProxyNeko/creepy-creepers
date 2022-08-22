/*
 * Creepy Creepers - https://github.com/tophatcats-mods/creepy-creepers
 * Copyright (C) 2016-2022 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * Specifically version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package dev.tophatcat.creepycreepers.entities;

import dev.tophatcat.creepycreepers.init.CreepyConfig;
import dev.tophatcat.creepycreepers.init.CreepyRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Random;

public class AustralianCreeperEntity extends Creeper {

    /**
     * Constructor for the creeper.
     *
     * @param type  The entity type.
     * @param level The current world.
     */
    public AustralianCreeperEntity(final EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    public static boolean canSpawn(EntityType<? extends AustralianCreeperEntity> creeper, ServerLevelAccessor world,
                                   MobSpawnType reason, BlockPos pos, Random random) {
        return pos.getY() < CreepyConfig.CONFIG.maxYLevelSpawnAustralian.get()
            && (world.getBlockState(pos.below()).is(Blocks.STONE)
            || world.getBlockState(pos.below()).is(Blocks.DEEPSLATE))
            && isDarkEnoughToSpawn(world, pos, random) && checkMobSpawnRules(creeper, world, reason, pos, random);
    }

    /**
     * Plays the step sound when the mob walks over a certain block.
     *
     * @param pos             The position to play the sound at.
     * @param blockUnderneath The block under the mob.
     */
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull BlockState blockUnderneath) {
    }

    /**
     * We override the default creeper explosion sound here and add our own.
     */
    @Override
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(CreepyRegistry.australian_creeper, 1.0F, 0.5F);
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
