package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import com.mcmoddev.creepycreepers.client.rendering.RenderGhostlyCreeper;
import com.mcmoddev.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CreepyCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRendering {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(GhostlyCreeperEntity.class, RenderGhostlyCreeper::new);
    }
}
