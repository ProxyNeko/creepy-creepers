package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.client.models.AustralianCreeperModel;
import cat.tophat.creepycreepers.client.models.GhostlyCreeperModel;
import cat.tophat.creepycreepers.client.rendering.CreepyCreeperRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryRendering {

	@SubscribeEvent
	public static void registerModels(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.GHOSTLY_CREEPER_ENTITY, manager -> new CreepyCreeperRenderer<>(manager, GhostlyCreeperModel::new, 0.0f, new ResourceLocation(CreepyCreepers.MOD_ID, "textures/entity/ghostly_creeper.png")));
		RenderingRegistry.registerEntityRenderingHandler(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY, manager -> new CreepyCreeperRenderer<>(manager, AustralianCreeperModel::new, 0.0f, new ResourceLocation(CreepyCreepers.MOD_ID, "textures/entity/australian_creeper.png")));
	}
}
