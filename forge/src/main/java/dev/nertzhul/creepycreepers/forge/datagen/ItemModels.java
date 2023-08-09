package dev.nertzhul.creepycreepers.forge.datagen;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.setup.CcItems;
import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModels extends ItemModelProvider {
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreepyCreepers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        CcItems.ITEMS.getEntries().stream().map(RegistryEntry::get)
            .filter(item -> item instanceof SpawnEggItem)
            .forEach(this::makeSpawnEgg);
    }

    protected void makeSpawnEgg(Item spawnEgg) {
        getBuilder(ForgeRegistries.ITEMS.getKey(spawnEgg).toString()).parent(getExistingFile(mcLoc("item/template_spawn_egg")));
    }
}
