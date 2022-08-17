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
package dev.tophatcat.creepycreepers.common.init;

import dev.tophatcat.creepycreepers.CreepyCreepers;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID)
public class CreepyEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeLoad(BiomeLoadingEvent event) {
        event.getSpawns().addSpawn(EntityClassification.MONSTER,
            new MobSpawner(CreepyEntityRegistry.GHOSTLY_CREEPER.get(),
                CreepyEventHandler.weight, 1, 5));
        /*event.getSpawns().addSpawn(EntityClassification.MONSTER,
            new MobSpawner(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY,
                RegistryEntity.weight, 1, 5));*/
    }

    @SubscribeEvent
    public static void serverAboutToStart(FMLServerAboutToStartEvent event) {
        event.getServer().registryAccess().registry(Registry.BIOME_REGISTRY).ifPresent(registry -> {
            registry.keySet().forEach(loc -> {
                registry.getOptional(loc).ifPresent(biome -> {
                    biome.getMobSettings().getMobs(EntityClassification.MONSTER).forEach(spawner -> {
                        if (spawner instanceof MobSpawner) {
                            if (CreepyEventHandler.allowlist != null && !CreepyEventHandler.allowlist.isEmpty()) {
                                if (!CreepyEventHandler.allowlist.contains(loc.toString())) {
                                    ((MobSpawner) spawner).invalidate();
                                } else {
                                    ((MobSpawner) spawner).validate();
                                }
                            } else if (CreepyEventHandler.disallowlist != null && !CreepyEventHandler.disallowlist.isEmpty()) {
                                if (CreepyEventHandler.disallowlist.contains(loc.toString())) {
                                    ((MobSpawner) spawner).invalidate();
                                } else {
                                    ((MobSpawner) spawner).validate();
                                }
                            }
                        }
                    });
                });
            });
        });
    }

    public static Set<String> allowlist, disallowlist;
    public static int weight;

    @SubscribeEvent
    public static void onLoad(ModConfig.Loading event) {
        allowlist = new HashSet<>(CreepyConfig.biomeAllowlist.get());
        disallowlist = new HashSet<>(CreepyConfig.biomeDisallowlist.get());
        weight = CreepyConfig.creeperSpawnWeight.get() == -1 ? 45 : CreepyConfig.creeperSpawnWeight.get();
    }

    @SubscribeEvent
    public static void onReload(ModConfig.Reloading event) {
        allowlist = new HashSet<>(CreepyConfig.biomeAllowlist.get());
        disallowlist = new HashSet<>(CreepyConfig.biomeDisallowlist.get());
        weight = CreepyConfig.creeperSpawnWeight.get() == -1 ? 45 : CreepyConfig.creeperSpawnWeight.get();
    }
}
