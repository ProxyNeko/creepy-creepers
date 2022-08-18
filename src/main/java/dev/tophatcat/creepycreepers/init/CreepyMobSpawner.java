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
package dev.tophatcat.creepycreepers.init;

import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class CreepyMobSpawner extends Spawners {

    private static final Field ITEM_WEIGHT = ObfuscationReflectionHelper
        .findField(WeightedRandom.Item.class, "field_76292_a");
    private final int weight;

    public CreepyMobSpawner(EntityType<?> type, int weight, int minCount, int maxCount) {
        super(type, weight, minCount, maxCount);
        this.weight = weight;
    }

    public void invalidate() {
        try {
            ITEM_WEIGHT.set(this, 0);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void validate() {
        try {
            ITEM_WEIGHT.set(this, weight);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
