package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.Registry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID)
public class EventHandler {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void biomeLoad(BiomeLoadingEvent event) {
		event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawner(RegistryEntity.GHOSTLY_CREEPER_ENTITY,
				RegistryEntity.weight, 1, 5));
		/*event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY,
				RegistryEntity.weight, 1, 5));*/
	}

	@SubscribeEvent
	public static void serverAboutToStart(FMLServerAboutToStartEvent event) {
		event.getServer().registryAccess().registry(Registry.BIOME_REGISTRY).ifPresent(registry -> {
			registry.keySet().forEach(loc -> {
				registry.getOptional(loc).ifPresent(biome -> {
					biome.getMobSettings().getMobs(MobCategory.MONSTER).forEach(spawner -> {
						if(spawner instanceof MobSpawner) {
							if(RegistryEntity.whitelist != null && !RegistryEntity.whitelist.isEmpty()) {
								if(!RegistryEntity.whitelist.contains(loc.toString())) {
									((MobSpawner) spawner).invalidate();
								} else {
									((MobSpawner) spawner).validate();
								}
							} else if(RegistryEntity.blacklist != null && !RegistryEntity.blacklist.isEmpty()) {
								if(RegistryEntity.blacklist.contains(loc.toString())) {
									((MobSpawner) spawner).invalidate();
								} else {
									((MobSpawner) spawner).validate();
								}
							}
						}
					});
				});
			});
		});
	}
}
