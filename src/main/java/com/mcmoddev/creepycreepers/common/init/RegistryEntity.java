package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import com.mcmoddev.creepycreepers.common.entities.AustralianCreeperEntity;
import com.mcmoddev.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEntity {

	@SuppressWarnings("unchecked")
	public static final EntityType<GhostlyCreeperEntity> GHOSTLY_CREEPER_ENTITY =
		(EntityType<GhostlyCreeperEntity>) EntityType.Builder.create(GhostlyCreeperEntity::new,
			EntityClassification.MONSTER)
			.size(0.6F, 1.7F)
			.setTrackingRange(80)
			.setUpdateInterval(1)
			.setShouldReceiveVelocityUpdates(true)
			.build(CreepyCreepers.MOD_ID + ":ghostly_creeper")
			.setRegistryName(CreepyCreepers.MOD_ID + ":ghostly_creeper");

	@SuppressWarnings("unchecked")
	public static final EntityType<AustralianCreeperEntity> AUSTRALIAN_CREEPER_ENTITY =
		(EntityType<AustralianCreeperEntity>) EntityType.Builder.create(AustralianCreeperEntity::new,
			EntityClassification.MONSTER)
			.size(0.6F, 1.7F)
			.setTrackingRange(80)
			.setUpdateInterval(1)
			.setShouldReceiveVelocityUpdates(true)
			.build(CreepyCreepers.MOD_ID + ":australian_creeper")
			.setRegistryName(CreepyCreepers.MOD_ID + ":australian_creeper");

	@SubscribeEvent
	@SuppressWarnings("unused")
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(
			GHOSTLY_CREEPER_ENTITY,
			AUSTRALIAN_CREEPER_ENTITY
		);

		EntitySpawnPlacementRegistry.register(GHOSTLY_CREEPER_ENTITY,
			EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
			MonsterEntity::func_223325_c);
		EntitySpawnPlacementRegistry.register(AUSTRALIAN_CREEPER_ENTITY,
			EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
			MonsterEntity::func_223325_c);
	}
}
