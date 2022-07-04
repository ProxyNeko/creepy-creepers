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
import dev.tophatcat.creepycreepers.common.entities.CreepyCreeperEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEntity {

    @ObjectHolder(CreepyCreepers.MOD_ID + ":ghostly_creeper")
    public static final EntityType<CreepyCreeperEntity> GHOSTLY_CREEPER_ENTITY = null;

    @ObjectHolder(CreepyCreepers.MOD_ID + ":australian_creeper")
    public static final EntityType<CreepyCreeperEntity> AUSTRALIAN_CREEPER_ENTITY = null;

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(

            EntityType.Builder.of((IFactory<CreepyCreeperEntity>) (type, world)
                    -> new CreepyCreeperEntity(type, world, ()
                    -> RegistrySound.CREEPER_SCREAM_SOUND), EntityClassification.MONSTER)
                .sized(0.6F, 1.7F)
                .setTrackingRange(80)
                .setUpdateInterval(1)
                .setShouldReceiveVelocityUpdates(true)
                .build(CreepyCreepers.MOD_ID + ":ghostly_creeper")
                .setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper"),

            EntityType.Builder.of((IFactory<CreepyCreeperEntity>) (type, world)
                    -> new CreepyCreeperEntity(type, world, ()
                    -> RegistrySound.CREEPER_SCREAM_SOUND), EntityClassification.MONSTER)
                .sized(0.6F, 1.7F)
                .setTrackingRange(80)
                .setUpdateInterval(1)
                .setShouldReceiveVelocityUpdates(true)
                .build(CreepyCreepers.MOD_ID + ":australian_creeper")
                .setRegistryName(CreepyCreepers.MOD_ID, "australian_creeper")
        );
    }

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(GHOSTLY_CREEPER_ENTITY,
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MonsterEntity::checkAnyLightMonsterSpawnRules);
            EntitySpawnPlacementRegistry.register(AUSTRALIAN_CREEPER_ENTITY,
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MonsterEntity::checkAnyLightMonsterSpawnRules);
            GlobalEntityTypeAttributes.put(GHOSTLY_CREEPER_ENTITY, CreeperEntity.createAttributes().build());
            GlobalEntityTypeAttributes.put(AUSTRALIAN_CREEPER_ENTITY, CreeperEntity.createAttributes().build());
        });
    }

    public static Set<String> whitelist, blacklist;
    public static int weight;

    @SubscribeEvent
    public static void onLoad(ModConfig.Loading event) {
        whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
        blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
        weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
    }

    @SubscribeEvent
    public static void onReload(ModConfig.Reloading event) {
        whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
        blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
        weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
    }
}
