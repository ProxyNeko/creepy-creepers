package dev.nertzhul.creepycreepers.forge.datagen.providers;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.setup.CcTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class BiomesTagsProvider extends BiomeTagsProvider {
    public BiomesTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper helper) {
        super(output, future, CreepyCreepers.MOD_ID, helper);
    }
    
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CcTags.IS_UNDERGROUND)
            .add(Biomes.DEEP_DARK)
            .add(Biomes.LUSH_CAVES)
            .add(Biomes.DRIPSTONE_CAVES);
    }
}
