package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrySound {

	public static final SoundEvent CREEPER_SCREAM_SOUND = getSound("creeperscream");

	@SubscribeEvent
	@SuppressWarnings("unused")
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().register(CREEPER_SCREAM_SOUND);
	}

	@SuppressWarnings("unused")
	private static SoundEvent getSound(ResourceLocation location) {
		return new SoundEvent(location).setRegistryName(location);
	}

	private static SoundEvent getSound(String location) {
		return getSound(new ResourceLocation(CreepyCreepers.MOD_ID, location));
	}
}
