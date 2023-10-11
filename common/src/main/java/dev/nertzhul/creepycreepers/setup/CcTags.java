package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class CcTags {
    public static void register() { }
    
    public static final TagKey<Biome> IS_UNDERGROUND = createBiomeTag("is_underground");
    
    private static TagKey<Biome> createBiomeTag(String pPath) {
        return TagKey.create(Registries.BIOME, CreepyCreepers.resource(pPath));
    }
}
