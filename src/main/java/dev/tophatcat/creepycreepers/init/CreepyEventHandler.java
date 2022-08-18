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

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class CreepyEventHandler {

    public static void biomeLoad(BiomeLoadingEvent event) {
        event.getSpawns().addSpawn(EntityClassification.MONSTER,
            new CreepyMobSpawner(CreepyEntityRegistry.GHOSTLY_CREEPER.get(),
                CreepyConfig.weight, 1, 5));
        /*event.getSpawns().addSpawn(EntityClassification.MONSTER,
            new MobSpawner(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY,
                RegistryEntity.weight, 1, 5));*/
    }

    public static void serverAboutToStart(FMLServerAboutToStartEvent event) {
        event.getServer().registryAccess().registry(Registry.BIOME_REGISTRY).ifPresent(registry -> {
            registry.keySet().forEach(location -> {
                registry.getOptional(location).ifPresent(biome -> {
                    biome.getMobSettings().getMobs(EntityClassification.MONSTER).forEach(spawner -> {
                        if (spawner instanceof CreepyMobSpawner) {
                            if (CreepyConfig.allowlist != null && !CreepyConfig.allowlist.isEmpty()) {
                                if (!CreepyConfig.allowlist.contains(location.toString())) {
                                    ((CreepyMobSpawner) spawner).invalidate();
                                } else {
                                    ((CreepyMobSpawner) spawner).validate();
                                }
                            } else if (CreepyConfig.disallowlist != null && !CreepyConfig.disallowlist.isEmpty()) {
                                if (CreepyConfig.disallowlist.contains(location.toString())) {
                                    ((CreepyMobSpawner) spawner).invalidate();
                                } else {
                                    ((CreepyMobSpawner) spawner).validate();
                                }
                            }
                        }
                    });
                });
            });
        });
    }
}
