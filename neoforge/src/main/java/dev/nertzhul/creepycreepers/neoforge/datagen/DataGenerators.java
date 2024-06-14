package dev.nertzhul.creepycreepers.neoforge.datagen;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.neoforge.datagen.providers.BiomesTagsProvider;
import dev.nertzhul.creepycreepers.neoforge.datagen.providers.ItemModelsProvider;
import dev.nertzhul.creepycreepers.neoforge.datagen.providers.LanguagesProvider;
import dev.nertzhul.creepycreepers.neoforge.datagen.providers.LootTablesProvider;
import dev.nertzhul.creepycreepers.neoforge.datagen.providers.SoundDefinitionsProvider;
import dev.nertzhul.creepycreepers.util.Translation;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        
        for (Translation.Locale locale : Translation.Locale.values()) {
            generator.addProvider(event.includeClient(), new LanguagesProvider(output, locale));
        }
        generator.addProvider(event.includeClient(), new ItemModelsProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, fileHelper));
        generator.addProvider(event.includeServer(), new BiomesTagsProvider(output, provider, fileHelper));
        generator.addProvider(event.includeServer(), new LootTablesProvider(output, provider));
    }
}
