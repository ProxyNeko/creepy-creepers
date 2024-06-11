package dev.nertzhul.creepycreepers.fabric;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.CorruptedCreeper;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.entities.TuffCreeper;
import dev.nertzhul.creepycreepers.fabric.network.FabricNetworkManager;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import dev.nertzhul.creepycreepers.setup.CcTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class CreepyCreepersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreepyCreepers.init();
        FabricNetworkManager.registerServerMessages();
        
        FabricDefaultAttributeRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.TUFF_CREEPER.get(), TuffCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.CORRUPTED_CREEPER.get(), CorruptedCreeper.createAttributes());
        
        SpawnPlacements.register(CcEntities.GHOSTLY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostlyCreeper::checkMonsterSpawnRules);
        SpawnPlacements.register(CcEntities.SNOWY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowyCreeper::checkCreeperSpawnRules);
        SpawnPlacements.register(CcEntities.HALLOWEEN_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HalloweenCreeper::checkMonsterSpawnRules);
        SpawnPlacements.register(CcEntities.TUFF_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TuffCreeper::checkCreeperSpawnRules);
        SpawnPlacements.register(CcEntities.CORRUPTED_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorruptedCreeper::checkMonsterSpawnRules);
        
        BiomeModifications.addSpawn(BiomeSelectors.tag(CcTags.HAS_GHOSTLY_CREEPER), MobCategory.MONSTER, CcEntities.GHOSTLY_CREEPER.get(), 50, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(CcTags.HAS_HALLOWEEN_CREEPER), MobCategory.MONSTER, CcEntities.HALLOWEEN_CREEPER.get(), 80, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(CcTags.HAS_SNOWY_CREEPER), MobCategory.MONSTER, CcEntities.SNOWY_CREEPER.get(), 80, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(CcTags.HAS_TUFF_CREEPER), MobCategory.MONSTER, CcEntities.TUFF_CREEPER.get(), 70, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(CcTags.HAS_CORRUPTED_CREEPER), MobCategory.MONSTER, CcEntities.CORRUPTED_CREEPER.get(), 10, 1, 1);
    }
}
