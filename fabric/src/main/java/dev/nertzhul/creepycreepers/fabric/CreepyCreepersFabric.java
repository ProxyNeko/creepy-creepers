package dev.nertzhul.creepycreepers.fabric;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.fabric.network.FabricNetworkManager;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class CreepyCreepersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreepyCreepers.init();
        FabricNetworkManager.registerServerMessages();
        
        FabricDefaultAttributeRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeper.createAttributes());
    }
}
