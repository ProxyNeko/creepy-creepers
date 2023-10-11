package dev.nertzhul.creepycreepers.forge.datagen;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.forge.datagen.providers.BiomesTagsProvider;
import dev.nertzhul.creepycreepers.forge.datagen.providers.ItemModelsProvider;
import dev.nertzhul.creepycreepers.forge.datagen.providers.LanguagesProvider;
import dev.nertzhul.creepycreepers.forge.datagen.providers.LootTablesProvider;
import dev.nertzhul.creepycreepers.forge.datagen.providers.SoundDefinitionsProvider;
import dev.nertzhul.creepycreepers.util.Translation;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        
        for (Translation.Locale locale : Translation.Locale.values()) {
            generator.addProvider(event.includeClient(), new LanguagesProvider(output, locale));
        }
        generator.addProvider(event.includeClient(), new ItemModelsProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, fileHelper));
        
        generator.addProvider(event.includeServer(), new BiomesTagsProvider(output, event.getLookupProvider(), fileHelper));
        generator.addProvider(event.includeServer(), new LootTablesProvider(output));
    }
}
