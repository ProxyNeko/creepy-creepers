package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import com.mcmoddev.creepycreepers.client.rendering.RenderAustralianCreeper;
import com.mcmoddev.creepycreepers.client.rendering.RenderGhostlyCreeper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRendering {

	@SubscribeEvent
	public static void registerModels(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.GHOSTLY_CREEPER_ENTITY, RenderGhostlyCreeper::new);
		RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY, RenderAustralianCreeper::new);
	}
}
