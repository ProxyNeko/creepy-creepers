package dev.nertzhul.creepycreepers.fabric;

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
import dev.nertzhul.creepycreepers.fabric.network.FabricClientNetworkManager;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.SpawnEggItem;

public class CreepyCreepersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CreepyCreepersClient.init();
        FabricClientNetworkManager.registerReceivers();
        
        EntityRendererRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.TUFF_CREEPER.get(), TuffCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.CORRUPTED_CREEPER.get(), CorruptedCreeperRenderer::new);
        
        EntityModelLayerRegistry.registerModelLayer(GhostlyCreeperModel.LAYER, () -> GhostlyCreeperModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(GhostlyCreeperModel.ARMOR_LAYER, () -> GhostlyCreeperModel.createBodyLayer(new CubeDeformation(1.5F)));
        EntityModelLayerRegistry.registerModelLayer(SnowyCreeperModel.LAYER, SnowyCreeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HalloweenCreeperModel.LAYER, HalloweenCreeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(TuffCreeperModel.LAYER, TuffCreeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CorruptedCreeperModel.LAYER, () -> CorruptedCreeperModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(CorruptedCreeperModel.ARMOR_LAYER, () -> CorruptedCreeperModel.createBodyLayer(new CubeDeformation(1.5F)));
        
        DispenserReadySpawnEgg.SPAWN_EGGS.forEach(pair -> {
            SpawnEggItem item = pair.getSecond();
            ColorProviderRegistry.ITEM.register((itemStack, i) -> FastColor.ARGB32.opaque(item.getColor(i)), item);
        });
    }
}
