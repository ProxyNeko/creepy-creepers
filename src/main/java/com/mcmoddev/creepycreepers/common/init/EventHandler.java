package com.mcmoddev.creepycreepers.common.init;

import com.google.common.collect.Sets;
import com.mcmoddev.creepycreepers.CreepyCreepers;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {

	private static Collection<Biome> biomes = null;
	private static Biome.SpawnListEntry entry = null;

	@SubscribeEvent
	@SuppressWarnings("unused")
	public static void onLoad(ModConfig.Loading event) {
		if (event.getConfig().getSpec() != Config.SERVER_SPECIFICATION) {
			return;
		}

		if (entry != null) {
			biomes.stream().map(biome -> biome.getSpawns(EntityClassification.MONSTER)).forEach(list
				-> list.remove(entry));
			biomes = Collections.emptyList();
		}

		if (Config.SERVER.CreepersSpawnNaturally.get()) {
			int currentWeight = Config.SERVER.CreeperSpawnWeight.get();

			if (currentWeight > 0) {
				biomes = ForgeRegistries.BIOMES.getValues();
				if (Config.SERVER.BiomeWhitelist.get() != null && Config.SERVER.BiomeWhitelist.get().size() > 0) {
					Set<String> whitelist = Sets.newHashSet(Config.SERVER.BiomeWhitelist.get());
					biomes = biomes.stream().filter(b
						-> whitelist.contains(b.getRegistryName().toString())).collect(Collectors.toList());
				} else {
					if (Config.SERVER.BiomeBlacklist.get() != null && Config.SERVER.BiomeBlacklist.get().size() > 0) {
						Set<String> blacklist = Sets.newHashSet(Config.SERVER.BiomeBlacklist.get());
						biomes = biomes.stream().filter(b
							-> !blacklist.contains(b.getRegistryName().toString())).collect(Collectors.toList());
					}
				}

				entry = new Biome.SpawnListEntry(RegistryEntity.GHOSTLY_CREEPER_ENTITY,
					Config.SERVER.CreeperSpawnWeight.get(), 1, 5);
				biomes.stream().map(biome -> biome.getSpawns(EntityClassification.MONSTER)).forEach(list
					-> list.add(entry));
			}
		}
	}
}
