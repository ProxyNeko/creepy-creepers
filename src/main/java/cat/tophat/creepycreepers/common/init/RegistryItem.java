package cat.tophat.creepycreepers.common.init;

import java.awt.Color;

import cat.tophat.creepycreepers.CreepyCreepers;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryItem {

	/**
	 * @param event Add items and block items to the item registry.
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			//Ghostly creeper.
			new ModdedSpawnEggItem(() -> RegistryEntity.GHOSTLY_CREEPER_ENTITY, Color.WHITE.getRGB(), Color.WHITE.getRGB(),
				new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper_spawn_egg"),
			//Australian creeper.
			new ModdedSpawnEggItem(() -> RegistryEntity.AUSTRALIAN_CREEPER_ENTITY, Color.BLUE.getRGB(), Color.WHITE.getRGB(),
				new Item.Properties().group(ItemGroup.MISC))
				.setRegistryName(CreepyCreepers.MOD_ID, "australian_creeper_spawn_egg")
		);
	}
	
	/**
	 * Exists to work around a limitation with Spawn Eggs:
	 * Spawn Eggs require an EntityType, but EntityTypes are created AFTER Items.
	 * Therefore it is "impossible" for modded spawn eggs to exist.
	 * To get around this we have our own custom SpawnEggItem, but it needs
	 * some extra setup after Item and EntityType registration completes.
	 * 
	 * @author Cadiboo
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModdedSpawnEggItem.initUnaddedEggs();
	}
}
