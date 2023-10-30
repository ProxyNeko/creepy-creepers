package dev.nertzhul.creepycreepers.forge;

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
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CreepyCreepersForgeClient {
    public static void init() {
        CreepyCreepersClient.init();
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modBus.addListener(CreepyCreepersForgeClient::onRegisterEntityRenderers);
        modBus.addListener(CreepyCreepersForgeClient::onEntityLayers);
        modBus.addListener(EventPriority.HIGHEST, CreepyCreepersForgeClient::onRegisterItemColors);
    }
    
    private static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        DispenserReadySpawnEgg.SPAWN_EGGS.forEach(pair -> {
            event.getItemColors().register((stack, layer) -> pair.getSecond().getColor(layer), pair.getSecond());
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
