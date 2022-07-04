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
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.awt.Color;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryItem {

    /**
     * @param event Add items and block items to the item registry.
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            //Ghostly creeper.
            new ModdedSpawnEggItem(() -> RegistryEntity.GHOSTLY_CREEPER_ENTITY,
                Color.WHITE.getRGB(), Color.WHITE.getRGB(), new Item.Properties().tab(ItemGroup.TAB_MISC))
                .setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper_spawn_egg"),
            //Australian creeper.
            new ModdedSpawnEggItem(() -> RegistryEntity.AUSTRALIAN_CREEPER_ENTITY,
                Color.BLUE.getRGB(), Color.WHITE.getRGB(), new Item.Properties().tab(ItemGroup.TAB_MISC))
                .setRegistryName(CreepyCreepers.MOD_ID, "australian_creeper_spawn_egg")
        );
    }

    /**
     * Exists to work around a limitation with Spawn Eggs:
     * Spawn Eggs require an EntityType, but EntityTypes are created AFTER Items.
     * Therefore, it is "impossible" for modded spawn eggs to exist.
     * To get around this we have our own custom SpawnEggItem, but it needs
     * some extra setup after Item and EntityType registration completes.
     *
     * @param event Add custom spawn eggs after registering entities
     * @author Cadiboo
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPostRegisterEntities(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModdedSpawnEggItem::initUnaddedEggs);
    }
}
