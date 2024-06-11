package dev.nertzhul.creepycreepers.forge.datagen.providers;

import com.google.common.collect.Lists;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.setup.CcTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.Nullable;
import java.util.List;
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
        
        List<ResourceKey<Biome>> used = Lists.newArrayList(MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().toList());
        used.removeIf((biome) -> biome.equals(Biomes.DEEP_DARK) || biome.equals(Biomes.MUSHROOM_FIELDS));
        
        TagsProvider.TagAppender<Biome> tag = this.tag(CcTags.HAS_CORRUPTED_CREEPER);
        used.forEach(tag::add);
        
        tag = this.tag(CcTags.HAS_GHOSTLY_CREEPER);
        used.forEach(tag::add);
        
        tag = this.tag(CcTags.HAS_HALLOWEEN_CREEPER);
        used.forEach(tag::add);
        
        tag = this.tag(CcTags.HAS_TUFF_CREEPER);
        used.forEach(tag::add);
        
        this.tag(CcTags.HAS_SNOWY_CREEPER)
            .addTag(BiomeTags.SPAWNS_SNOW_FOXES);
    }
}
