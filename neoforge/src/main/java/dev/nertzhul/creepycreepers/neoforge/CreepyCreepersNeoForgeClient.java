package dev.nertzhul.creepycreepers.neoforge;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.CreepyCreepersClient;
import dev.nertzhul.creepycreepers.client.rendering.corruptedcreeper.CorruptedCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.corruptedcreeper.CorruptedCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper.GhostlyCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper.GhostlyCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.halloweencreeper.HalloweenCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.halloweencreeper.HalloweenCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.snowycreeper.SnowyCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.snowycreeper.SnowyCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.tuffcreeper.TuffCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.tuffcreeper.TuffCreeperRenderer;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod(value = CreepyCreepers.MOD_ID, dist = Dist.CLIENT)
public class CreepyCreepersNeoForgeClient {
    public CreepyCreepersNeoForgeClient(IEventBus modEventBus) {
        CreepyCreepersClient.init();
        
        modEventBus.addListener(CreepyCreepersNeoForgeClient::onRegisterEntityRenderers);
        modEventBus.addListener(CreepyCreepersNeoForgeClient::onEntityLayers);
        modEventBus.addListener(EventPriority.HIGHEST, CreepyCreepersNeoForgeClient::onRegisterItemColors);
    }
    
    private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        DispenserReadySpawnEgg.SPAWN_EGGS.forEach(pair -> {
            event.getItemColors().register((stack, layer) -> FastColor.ARGB32.opaque(pair.getSecond().getColor(layer)), pair.getSecond());
        });
    }
    
    private static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeperRenderer::new);
        event.registerEntityRenderer(CcEntities.SNOWY_CREEPER.get(), SnowyCreeperRenderer::new);
        event.registerEntityRenderer(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeperRenderer::new);
        event.registerEntityRenderer(CcEntities.TUFF_CREEPER.get(), TuffCreeperRenderer::new);
        event.registerEntityRenderer(CcEntities.CORRUPTED_CREEPER.get(), CorruptedCreeperRenderer::new);
    }
    
    private static void onEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GhostlyCreeperModel.LAYER, () -> GhostlyCreeperModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(GhostlyCreeperModel.ARMOR_LAYER, () -> GhostlyCreeperModel.createBodyLayer(new CubeDeformation(1.5F)));
        event.registerLayerDefinition(SnowyCreeperModel.LAYER, SnowyCreeperModel::createBodyLayer);
        event.registerLayerDefinition(HalloweenCreeperModel.LAYER, HalloweenCreeperModel::createBodyLayer);
        event.registerLayerDefinition(TuffCreeperModel.LAYER, TuffCreeperModel::createBodyLayer);
        event.registerLayerDefinition(CorruptedCreeperModel.LAYER, () -> CorruptedCreeperModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(CorruptedCreeperModel.ARMOR_LAYER, () -> CorruptedCreeperModel.createBodyLayer(new CubeDeformation(1.5F)));
    }
}
