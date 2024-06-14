package dev.nertzhul.creepycreepers.forge;

import com.mojang.datafixers.util.Pair;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.CorruptedCreeper;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.entities.TuffCreeper;
import dev.nertzhul.creepycreepers.forge.network.ForgeNetworkManager;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.mixin.SpawnEggAccessor;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Map;
import java.util.function.Supplier;

@Mod(CreepyCreepers.MOD_ID)
public class CreepyCreepersForge {
    public CreepyCreepersForge() {
        CreepyCreepers.init();
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(CreepyCreepersForge::onCommonSetup);
        modEventBus.addListener(CreepyCreepersForge::onEntityAttributeCreation);
        modEventBus.addListener(CreepyCreepersForge::onSpawnPlacement);
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CreepyCreepersForgeClient.init();
        }
    }
    
    private static void onCommonSetup(FMLCommonSetupEvent event) {
        ForgeNetworkManager.registerMessages();
        
        Map<EntityType<? extends Mob>, SpawnEggItem> idMap = SpawnEggAccessor.cc_getIdMap();
        for (Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem> pair : DispenserReadySpawnEgg.SPAWN_EGGS) {
            idMap.put(pair.getFirst().get(), pair.getSecond());
        }
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
