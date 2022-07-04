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
import dev.tophatcat.creepycreepers.client.models.AustralianCreeperModel;
import dev.tophatcat.creepycreepers.client.models.GhostlyCreeperModel;
import dev.tophatcat.creepycreepers.client.rendering.CreepyCreeperRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRendering {

    @SubscribeEvent
    public static void registerModels(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.GHOSTLY_CREEPER_ENTITY,
            manager -> new CreepyCreeperRenderer<>(manager, GhostlyCreeperModel::new, 0.0f,
                new ResourceLocation(CreepyCreepers.MOD_ID, "textures/entity/ghostly_creeper.png")));
        RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY,
            manager -> new CreepyCreeperRenderer<>(manager, AustralianCreeperModel::new, 0.0f,
                new ResourceLocation(CreepyCreepers.MOD_ID, "textures/entity/australian_creeper.png")));
    }
}
