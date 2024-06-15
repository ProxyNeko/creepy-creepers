package dev.nertzhul.creepycreepers.util;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RegistryUtil {
    public static ResourceLocation getKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
    
    public static ResourceLocation getKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
    
    public static ResourceLocation getKey(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }
    
    public static ResourceLocation getKey(ParticleType<?> particleType) {
        return BuiltInRegistries.PARTICLE_TYPE.getKey(particleType);
    }
}
