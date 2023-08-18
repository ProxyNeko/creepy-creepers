package dev.nertzhul.creepycreepers.quilt;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class CreepyCreepersQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        CreepyCreepers.init();
        
        FabricDefaultAttributeRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeper.createAttributes());
    }
}
