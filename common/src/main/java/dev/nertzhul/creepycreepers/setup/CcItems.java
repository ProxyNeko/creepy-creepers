package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.util.registry.RegistryObject;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class CcItems {
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, CreepyCreepers.MOD_ID);
    
    public static final RegistryObject<SpawnEggItem> CORRUPTED_CREEPER_SPAWN_EGG = ITEMS.register("corrupted_creeper_spawn_egg", () -> new DispenserReadySpawnEgg(CcEntities.CORRUPTED_CREEPER, 0x072D10, 0x1B7A32, new Item.Properties()));
    public static final RegistryObject<SpawnEggItem> GHOSTLY_CREEPER_SPAWN_EGG = ITEMS.register("ghostly_creeper_spawn_egg", () -> new DispenserReadySpawnEgg(CcEntities.GHOSTLY_CREEPER, 0xFFFFFF, 0x808080, new Item.Properties()));
    public static final RegistryObject<SpawnEggItem> SNOWY_CREEPER_SPAWN_EGG = ITEMS.register("snowy_creeper_spawn_egg", () -> new DispenserReadySpawnEgg(CcEntities.SNOWY_CREEPER, 0xD1FFFF, 0x53DDFF, new Item.Properties()));
    public static final RegistryObject<SpawnEggItem> HALLOWEEN_CREEPER_SPAWN_EGG = ITEMS.register("halloween_creeper_spawn_egg", () -> new DispenserReadySpawnEgg(CcEntities.HALLOWEEN_CREEPER, 0x528A52, 0x693400, new Item.Properties()));
    public static final RegistryObject<SpawnEggItem> TUFF_CREEPER_SPAWN_EGG = ITEMS.register("tuff_creeper_spawn_egg", () -> new DispenserReadySpawnEgg(CcEntities.TUFF_CREEPER, 0x6A6E6F, 0x5D5D52, new Item.Properties()));
    
    public static void register() {
        // no-op
    }
}
