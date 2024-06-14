package dev.nertzhul.creepycreepers.neoforge.datagen.providers;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.setup.CcItems;
import dev.nertzhul.creepycreepers.util.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {
    public ItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreepyCreepers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        CcItems.ITEMS.getEntries().stream().map(RegistryObject::get)
            .filter(SpawnEggItem.class::isInstance)
            .forEach(this::makeSpawnEgg);
    }

    protected void makeSpawnEgg(Item spawnEgg) {
        getBuilder(BuiltInRegistries.ITEM.getKey(spawnEgg).toString()).parent(getExistingFile(mcLoc("item/template_spawn_egg")));
    }
}
