package cat.tophat.creepycreepers.common.init;

import java.util.HashSet;
import java.util.Set;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.common.entities.AustralianCreeperEntity;
import cat.tophat.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEntity {

	@ObjectHolder(CreepyCreepers.MOD_ID + ":ghostly_creeper")
	public static final EntityType<GhostlyCreeperEntity> GHOSTLY_CREEPER_ENTITY = null;

	@ObjectHolder(CreepyCreepers.MOD_ID + ":australian_creeper")
	public static final EntityType<AustralianCreeperEntity> AUSTRALIAN_CREEPER_ENTITY = null;

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(
				EntityType.Builder.create(GhostlyCreeperEntity::new,
						EntityClassification.MONSTER)
						.size(0.6F, 1.7F)
						.setTrackingRange(80)
						.setUpdateInterval(1)
						.setShouldReceiveVelocityUpdates(true)
						.build(CreepyCreepers.MOD_ID + ":ghostly_creeper")
						.setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper"),
				EntityType.Builder.create(AustralianCreeperEntity::new,
						EntityClassification.MONSTER)
						.size(0.6F, 1.7F)
						.setTrackingRange(80)
						.setUpdateInterval(1)
						.setShouldReceiveVelocityUpdates(true)
						.build(CreepyCreepers.MOD_ID + ":australian_creeper")
						.setRegistryName(CreepyCreepers.MOD_ID, "australian_creeper")
		);
	}

	@SubscribeEvent
	public static void common(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EntitySpawnPlacementRegistry.register(GHOSTLY_CREEPER_ENTITY,
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::canMonsterSpawnInLight);
			EntitySpawnPlacementRegistry.register(AUSTRALIAN_CREEPER_ENTITY,
					EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
					MonsterEntity::canMonsterSpawnInLight);
			GlobalEntityTypeAttributes.put(GHOSTLY_CREEPER_ENTITY, CreeperEntity.registerAttributes().create());
			GlobalEntityTypeAttributes.put(AUSTRALIAN_CREEPER_ENTITY, CreeperEntity.registerAttributes().create());
		});
	}
	
	public static Set<String> whitelist, blacklist;
	public static int weight;

	@SubscribeEvent
	public static void onLoad(ModConfig.Loading event) {
		whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
		blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
		weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
	}
	
	@SubscribeEvent
	public static void onReload(ModConfig.Reloading event) {
		whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
		blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
		weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
	}
}
