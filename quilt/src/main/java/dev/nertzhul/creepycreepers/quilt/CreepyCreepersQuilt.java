package dev.nertzhul.creepycreepers.quilt;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.entities.TuffCreeper;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class CreepyCreepersQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        CreepyCreepers.init();
        
        FabricDefaultAttributeRegistry.register(CcEntities.GHOSTLY_CREEPER.get(), GhostlyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.SNOWY_CREEPER.get(), SnowyCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.HALLOWEEN_CREEPER.get(), HalloweenCreeper.createAttributes());
        FabricDefaultAttributeRegistry.register(CcEntities.TUFF_CREEPER.get(), TuffCreeper.createAttributes());
        
        SpawnPlacements.register(CcEntities.GHOSTLY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhostlyCreeper::checkMonsterSpawnRules);
        SpawnPlacements.register(CcEntities.SNOWY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowyCreeper::checkCreeperSpawnRules);
        SpawnPlacements.register(CcEntities.HALLOWEEN_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HalloweenCreeper::checkMonsterSpawnRules);
        SpawnPlacements.register(CcEntities.TUFF_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TuffCreeper::checkCreeperSpawnRules);
        
        BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, CcEntities.GHOSTLY_CREEPER.get(), 50, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, CcEntities.HALLOWEEN_CREEPER.get(), 80, 1, 3);
        /* minecraft:spawns_snow_foxes has the same biomes as #forge:is_snowy */
        BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.SPAWNS_SNOW_FOXES), MobCategory.MONSTER, CcEntities.SNOWY_CREEPER.get(), 80, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.isIn(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, CcEntities.TUFF_CREEPER.get(), 70, 1, 3);
    }
}
