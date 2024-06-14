package dev.nertzhul.creepycreepers.neoforge;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.CorruptedCreeper;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.entities.TuffCreeper;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.mixin.SpawnEggAccessor;
import dev.nertzhul.creepycreepers.neoforge.network.NeoForgeNetworkManager;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;

@Mod(CreepyCreepers.MOD_ID)
public class CreepyCreepersNeoForge {
    public CreepyCreepersNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        CreepyCreepers.init();
        
        modEventBus.addListener(NeoForgeNetworkManager::register);
        modEventBus.addListener(CreepyCreepersNeoForge::onCommonSetup);
        modEventBus.addListener(CreepyCreepersNeoForge::onEntityAttributeCreation);
        modEventBus.addListener(CreepyCreepersNeoForge::onSpawnPlacement);
    }
    
    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            var idMap = SpawnEggAccessor.cc_getIdMap();
            for (var pair : DispenserReadySpawnEgg.SPAWN_EGGS) {
                idMap.put(pair.getFirst().get(), pair.getSecond());
            }
        });
    }
    
    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeper.createAttributes().build());
        event.put(CcEntities.SNOWY_CREEPER.get(), SnowyCreeper.createAttributes().build());
        event.put(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeper.createAttributes().build());
        event.put(CcEntities.TUFF_CREEPER.get(), TuffCreeper.createAttributes().build());
        event.put(CcEntities.CORRUPTED_CREEPER.get(), CorruptedCreeper.createAttributes().build());
    }
    
    private static void onSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(CcEntities.GHOSTLY_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostlyCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(CcEntities.HALLOWEEN_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HalloweenCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(CcEntities.SNOWY_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowyCreeper::checkCreeperSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(CcEntities.TUFF_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TuffCreeper::checkCreeperSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(CcEntities.CORRUPTED_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorruptedCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
