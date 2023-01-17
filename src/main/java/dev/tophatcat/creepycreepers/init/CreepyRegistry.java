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
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;

public class CreepyRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, CreepyCreepers.MOD_ID);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
        ForgeRegistries.SOUND_EVENTS, CreepyCreepers.MOD_ID);

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
        ForgeRegistries.ENTITY_TYPES, CreepyCreepers.MOD_ID);

    public static final RegistryObject<EntityType<GhostlyCreeperEntity>> GHOSTLY_CREEPER = ENTITIES.register(
        "ghostly_creeper", () -> EntityType.Builder.of(GhostlyCreeperEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.7F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(new ResourceLocation(CreepyCreepers.MOD_ID, "ghostly_creeper").toString())
    );

    public static final RegistryObject<EntityType<AustralianCreeperEntity>> AUSTRALIAN_CREEPER = ENTITIES.register(
        "australian_creeper", () -> EntityType.Builder.of(AustralianCreeperEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.7F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(new ResourceLocation(CreepyCreepers.MOD_ID, "australian_creeper").toString())
    );

    private static final RegistryObject<SpawnEggItem> GHOSTLY_CREEPER_SPAWN_EGG = ITEMS.register(
        "ghostly_creeper_spawn_egg", () -> new ForgeSpawnEggItem(GHOSTLY_CREEPER, 0xFFFFFF,
            0x808080, new Item.Properties()));

    private static final RegistryObject<SpawnEggItem> AUSTRALIAN_CREEPER_SPAWN_EGG = ITEMS.register(
        "australian_creeper_spawn_egg", () -> new ForgeSpawnEggItem(AUSTRALIAN_CREEPER, 0x0000FF,
            0xFFFFFF, new Item.Properties()));


    public static final RegistryObject<SoundEvent> GHOSTLY_CREEPER_SOUND = SOUNDS.register(
        "ghostly_creeper_scream", () -> SoundEvent.createVariableRangeEvent(
            new ResourceLocation(CreepyCreepers.MOD_ID, "ghostly_creeper_scream")));
    public static final RegistryObject<SoundEvent> AUSTRALIAN_CREEPER_SOUND = SOUNDS.register(
        "australian_creeper", () -> SoundEvent.createVariableRangeEvent(
            new ResourceLocation(CreepyCreepers.MOD_ID, "australian_creeper")));

    public static void registerSpawns(SpawnPlacementRegisterEvent event) {
        event.register(
            GHOSTLY_CREEPER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            GhostlyCreeperEntity::canSpawn,
            SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
            AUSTRALIAN_CREEPER.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            AustralianCreeperEntity::canSpawn,
            SpawnPlacementRegisterEvent.Operation.OR
        );
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(GHOSTLY_CREEPER.get(), Creeper.createAttributes().build());
        event.put(AUSTRALIAN_CREEPER.get(), Creeper.createAttributes().build());
    }

    public static void addToCreativeTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(GHOSTLY_CREEPER_SPAWN_EGG.get());
            event.accept(AUSTRALIAN_CREEPER_SPAWN_EGG.get());
        }
    }
}
