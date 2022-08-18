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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CreepyEntityRegistry {

    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(
        ForgeRegistries.ITEMS, CreepyCreepers.MOD_ID);
    public static final DeferredRegister<EntityType<?>> CREEPERS = DeferredRegister.create(
        ForgeRegistries.ENTITIES, CreepyCreepers.MOD_ID);

    public static final RegistryObject<EntityType<CreeperEntity>> GHOSTLY_CREEPER = registerEntity(
        "ghostly_creeper", GhostlyCreeperEntity::new, true,
        0.6F, 1.7F, 0xFFFFFF, 0xFFFFFF);

    public static final RegistryObject<EntityType<CreeperEntity>> AUSTRALIAN_CREEPER = registerEntity(
        "australian_creeper", AustralianCreeperEntity::new, false,
        0.6F, 1.7F, 0xFFFFFF, 0x0000FF);

    private static <T extends Entity> RegistryObject<EntityType<T>>
    registerEntity(String registryName, EntityType.IFactory<T> factory, boolean fireImmune, float width, float height,
                   int primaryEggColor, int secondaryEggColor) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, EntityClassification.MONSTER)
            .sized(width, height);
        if (fireImmune) {
            builder.fireImmune();
        }

        EntityType<T> entityType = builder.build(registryName);
        RegistryObject<EntityType<T>> registryObject = CREEPERS.register(registryName, () -> entityType);

        System.out.println(primaryEggColor + " " + secondaryEggColor);
        if (primaryEggColor != -1) {
            SPAWN_EGGS.register(registryName + "_spawn_egg", () -> new ForgeSpawnEggItem(registryObject,
                primaryEggColor, secondaryEggColor, new Item.Properties().tab(ItemGroup.TAB_MISC)));
        }
        return registryObject;
    }

    public static void registerCreeperContent(final FMLCommonSetupEvent event) {
        event.enqueueWork(CreepyEntityRegistry::registerSpawnPlacements);
    }

    private static void registerSpawnPlacements() {
        EntitySpawnPlacementRegistry.register(GHOSTLY_CREEPER.get(),
            EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            MonsterEntity::checkAnyLightMonsterSpawnRules);
        EntitySpawnPlacementRegistry.register(AUSTRALIAN_CREEPER.get(),
            EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
            MonsterEntity::checkAnyLightMonsterSpawnRules);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(GHOSTLY_CREEPER.get(), CreeperEntity.createAttributes().build());
        event.put(AUSTRALIAN_CREEPER.get(), CreeperEntity.createAttributes().build());
    }
}
