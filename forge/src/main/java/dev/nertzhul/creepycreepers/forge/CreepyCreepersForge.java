package dev.nertzhul.creepycreepers.forge;

import com.mojang.datafixers.util.Pair;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.forge.datagen.ItemModels;
import dev.nertzhul.creepycreepers.forge.datagen.LanguagesProvider;
import dev.nertzhul.creepycreepers.forge.datagen.LootTablesProvider;
import dev.nertzhul.creepycreepers.forge.datagen.SoundDefinitions;
import dev.nertzhul.creepycreepers.forge.network.ForgeNetworkManager;
import dev.nertzhul.creepycreepers.items.DispenserReadySpawnEgg;
import dev.nertzhul.creepycreepers.mixin.SpawnEggAccessor;
import dev.nertzhul.creepycreepers.setup.CcEntities;
import dev.nertzhul.creepycreepers.util.Translation;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Map;
import java.util.function.Supplier;

@Mod(CreepyCreepers.MOD_ID)
public class CreepyCreepersForge {
    public CreepyCreepersForge() {
        CreepyCreepers.init();
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener((FMLCommonSetupEvent event) -> {
            ForgeNetworkManager.registerMessages();
            
            Map<EntityType<? extends Mob>, SpawnEggItem> idMap = SpawnEggAccessor.cc$getIdMap();
            for (Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem> pair : DispenserReadySpawnEgg.SPAWN_EGGS) {
                idMap.put(pair.getFirst().get(), pair.getSecond());
            }
        });
        
        modEventBus.addListener((EntityAttributeCreationEvent event) -> {
            event.put(CcEntities.GHOSTLY_CREEPER.get(), Creeper.createAttributes().build());
            event.put(CcEntities.SNOWY_CREEPER.get(), Creeper.createAttributes().build());
            event.put(CcEntities.HALLOWEEN_CREEPER.get(), Creeper.createAttributes().build());
        });
        
        modEventBus.addListener((GatherDataEvent event) -> {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();
            
            for (Translation.Locale locale : Translation.Locale.values()) {
                generator.addProvider(event.includeClient(), new LanguagesProvider(output, locale));
            }
            generator.addProvider(event.includeClient(), new ItemModels(output, fileHelper));
            generator.addProvider(event.includeClient(), new SoundDefinitions(output, fileHelper));
            
            generator.addProvider(event.includeServer(), new LootTablesProvider(output));
        });
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CreepyCreepersForgeClient.init();
        }
    }
}
