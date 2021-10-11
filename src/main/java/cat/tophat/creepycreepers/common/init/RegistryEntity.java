package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.common.entities.CreepyCreeperEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEntity {

	@ObjectHolder(CreepyCreepers.MOD_ID + ":ghostly_creeper")
	public static final EntityType<CreepyCreeperEntity> GHOSTLY_CREEPER_ENTITY = null;

	@ObjectHolder(CreepyCreepers.MOD_ID + ":australian_creeper")
	public static final EntityType<CreepyCreeperEntity> AUSTRALIAN_CREEPER_ENTITY = null;

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(
				EntityType.Builder.of(new EntityFactory<CreepyCreeperEntity>() {
					@Override
					public CreepyCreeperEntity create(EntityType<CreepyCreeperEntity> type, Level world) {
						return new CreepyCreeperEntity(type, world, () -> RegistrySound.CREEPER_SCREAM_SOUND);
					}
				}, MobCategory.MONSTER)
						.sized(0.6F, 1.7F)
						.setTrackingRange(80)
						.setUpdateInterval(1)
						.setShouldReceiveVelocityUpdates(true)
						.build(CreepyCreepers.MOD_ID + ":ghostly_creeper")
						.setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper"),
				EntityType.Builder.of(new EntityFactory<CreepyCreeperEntity>() {
					@Override
					public CreepyCreeperEntity create(EntityType<CreepyCreeperEntity> type, Level world) {
						return new CreepyCreeperEntity(type, world, () -> RegistrySound.CREEPER_SCREAM_SOUND);
					}
				}, MobCategory.MONSTER)
						.sized(0.6F, 1.7F)
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
			SpawnPlacements.register(GHOSTLY_CREEPER_ENTITY,
					SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					Monster::checkMonsterSpawnRules);
			SpawnPlacements.register(AUSTRALIAN_CREEPER_ENTITY,
					SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					Monster::checkMonsterSpawnRules);
			DefaultAttributes.put(GHOSTLY_CREEPER_ENTITY, Creeper.createAttributes().build());
			DefaultAttributes.put(AUSTRALIAN_CREEPER_ENTITY, Creeper.createAttributes().build());
		});
	}
	
	public static Set<String> whitelist, blacklist;
	public static int weight;

	@SubscribeEvent
	public static void onLoad(ModConfigEvent.Loading event) {
		whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
		blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
		weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
	}
	
	@SubscribeEvent
	public static void onReload(ModConfigEvent.Reloading event) {
		whitelist = new HashSet<>(Config.SERVER.BiomeWhitelist.get());
		blacklist = new HashSet<>(Config.SERVER.BiomeBlacklist.get());
		weight = Config.SERVER.CreeperSpawnWeight.get() == -1 ? 45 : Config.SERVER.CreeperSpawnWeight.get();
	}
}
