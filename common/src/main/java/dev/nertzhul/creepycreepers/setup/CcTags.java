package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class CcTags {
    public static final TagKey<Biome> IS_UNDERGROUND = createBiomeTag("is_underground");
    
    public static final TagKey<Biome> HAS_GHOSTLY_CREEPER = createBiomeTag("has_ghostly_creeper");
    public static final TagKey<Biome> HAS_HALLOWEEN_CREEPER = createBiomeTag("has_halloween_creeper");
    public static final TagKey<Biome> HAS_SNOWY_CREEPER = createBiomeTag("has_snowy_creeper");
    public static final TagKey<Biome> HAS_TUFF_CREEPER = createBiomeTag("has_tuff_creeper");
    public static final TagKey<Biome> HAS_CORRUPTED_CREEPER = createBiomeTag("has_corrupted_creeper");
    
    private static TagKey<Biome> createBiomeTag(String pPath) {
        return TagKey.create(Registries.BIOME, CreepyCreepers.resource(pPath));
    }
    
    public static void register() {
        // no-op
    }
}
