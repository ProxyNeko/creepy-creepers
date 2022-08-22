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
package dev.tophatcat.creepycreepers.client;

import dev.tophatcat.creepycreepers.client.models.AustralianCreeperModel;
import dev.tophatcat.creepycreepers.client.models.GhostlyCreeperModel;
import dev.tophatcat.creepycreepers.client.rendering.AustralianCreeperRenderer;
import dev.tophatcat.creepycreepers.client.rendering.GhostlyCreeperRenderer;
import dev.tophatcat.creepycreepers.init.CreepyRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class CreeperRenderingRegistry {

    public static void registerEntityModels(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CreepyRegistry.GHOSTLY_CREEPER.get(),
            GhostlyCreeperRenderer::new);
        event.registerEntityRenderer(CreepyRegistry.AUSTRALIAN_CREEPER.get(),
            AustralianCreeperRenderer::new);
    }

    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GhostlyCreeperModel.GHOSTLY_CREEPER_LAYER_LOCATION,
            GhostlyCreeperModel::createBodyLayer);
        event.registerLayerDefinition(AustralianCreeperModel.AUSTRALIAN_CREEPER_LAYER_LOCATION,
            AustralianCreeperModel::createBodyLayer);
    }
}
