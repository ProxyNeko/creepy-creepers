package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.client.rendering.RenderAustralianCreeper;
import cat.tophat.creepycreepers.client.rendering.RenderGhostlyCreeper;
import cat.tophat.creepycreepers.common.entities.AustralianCreeperEntity;
import cat.tophat.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRendering {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(GhostlyCreeperEntity.class, RenderGhostlyCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(AustralianCreeperEntity.class, RenderAustralianCreeper::new);
    }
}
