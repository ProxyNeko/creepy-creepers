package dev.nertzhul.creepycreepers.quilt;

import dev.nertzhul.creepycreepers.CreepyCreepersClient;
import dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper.GhostlyCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper.GhostlyCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.halloweencreeper.HalloweenCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.halloweencreeper.HalloweenCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.snowycreeper.SnowyCreeperModel;
import dev.nertzhul.creepycreepers.client.rendering.snowycreeper.SnowyCreeperRenderer;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.quilt.network.QuiltNetworkManager;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.item.SpawnEggItem;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class CreepyCreepersQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        CreepyCreepersClient.init();
        QuiltNetworkManager.registerClientMessages();
        
        EntityRendererRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeperRenderer::new);
        EntityRendererRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeperRenderer::new);
        
        EntityModelLayerRegistry.registerModelLayer(GhostlyCreeperModel.LAYER, GhostlyCreeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SnowyCreeperModel.LAYER, SnowyCreeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HalloweenCreeperModel.LAYER, HalloweenCreeperModel::createBodyLayer);
        
        DispenserReadySpawnEgg.SPAWN_EGGS.forEach(pair -> {
            SpawnEggItem item = pair.getSecond();
            ColorProviderRegistry.ITEM.register((itemStack, i) -> item.getColor(i), item);
        });
    }
}
