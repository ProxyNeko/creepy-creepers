package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryItem {

    /**
     * @param event Add items and block items to the item registry.
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                //Ghostly creeper.
                new SpawnEggItem(RegistryEntity.GHOSTLY_CREEPER_ENTITY, Color.WHITE.getRGB(), Color.WHITE.getRGB(),
                        new Item.Properties().group(ItemGroup.MISC))
                        .setRegistryName(CreepyCreepers.MOD_ID, "ghostly_creeper_spawn_egg"),
                //Australian creeper.
                new SpawnEggItem(RegistryEntity.AUSTRALIAN_CREEPER_ENTITY, Color.BLUE.getRGB(), Color.WHITE.getRGB(),
                        new Item.Properties().group(ItemGroup.MISC))
                        .setRegistryName(CreepyCreepers.MOD_ID, "australian_creeper_spawn_egg")
        );
    }
}
