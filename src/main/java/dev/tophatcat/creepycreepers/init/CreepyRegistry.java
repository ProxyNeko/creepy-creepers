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

import dev.tophatcat.creepycreepers.CreepyCreepers;
import dev.tophatcat.creepycreepers.entities.AustralianCreeperEntity;
import dev.tophatcat.creepycreepers.entities.GhostlyCreeperEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.ObjectHolder;

public class CreepyRegistry {

    @ObjectHolder(CreepyCreepers.MOD_ID + ":ghostly_creeper_scream")
    public static SoundEvent ghostly_creeper_scream;

    @ObjectHolder(CreepyCreepers.MOD_ID + ":australian_creeper")
    public static SoundEvent australian_creeper;

    public static void registerSound(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
            ghostly_creeper_scream = new SoundEvent(new ResourceLocation(
                CreepyCreepers.MOD_ID, "ghostly_creeper_scream")).setRegistryName("ghostly_creeper_scream"),
            australian_creeper = new SoundEvent(new ResourceLocation(
                CreepyCreepers.MOD_ID, "australian_creeper")).setRegistryName("australian_creeper"));
    }

    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
            GHOSTLY_CREEPER.get().setRegistryName("ghostly_creeper"),
            AUSTRALIAN_CREEPER.get().setRegistryName("australian_creeper")
        );

        SpawnPlacements.register(GHOSTLY_CREEPER.get(),
            SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            GhostlyCreeperEntity::canSpawn);
        SpawnPlacements.register(AUSTRALIAN_CREEPER.get(),
            SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            AustralianCreeperEntity::canSpawn);
    }

    @SuppressWarnings("deprecation")
    public static void registerEggs(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new SpawnEggItem(GHOSTLY_CREEPER.get(), 0xFFFFFF, 0x808080,
                new Item.Properties().tab(CreativeModeTab.TAB_MISC)).setRegistryName("ghostly_creeper_spawn_egg"),
            new SpawnEggItem(AUSTRALIAN_CREEPER.get(), 0x0000FF, 0xFFFFFF,
                new Item.Properties().tab(CreativeModeTab.TAB_MISC)).setRegistryName("australian_creeper_spawn_egg")
        );
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(GHOSTLY_CREEPER.get(), Creeper.createAttributes().build());
        event.put(AUSTRALIAN_CREEPER.get(), Creeper.createAttributes().build());
    }

    public static final NonNullLazy<EntityType<GhostlyCreeperEntity>> GHOSTLY_CREEPER = NonNullLazy.of(
        () -> EntityType.Builder.of(GhostlyCreeperEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.7F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(new ResourceLocation(CreepyCreepers.MOD_ID, "ghostly_creeper").toString())
    );

    public static final NonNullLazy<EntityType<AustralianCreeperEntity>> AUSTRALIAN_CREEPER = NonNullLazy.of(
        () -> EntityType.Builder.of(AustralianCreeperEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.7F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(new ResourceLocation(CreepyCreepers.MOD_ID, "australian_creeper").toString())
    );
}
