package dev.nertzhul.creepycreepers.util;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public record Translation(String key, Map<String, String> localeMap) {
    public Component asComponent() {
        return Component.translatable(key());
    }
    
    public Component asComponent(Object... args) {
        return Component.translatable(key(), args);
    }
    
    public static Builder builder(Item item) {
        return new Builder(item.getDescriptionId());
    }
    
    public static Builder builder(EntityType<?> entityType) {
        return new Builder(entityType.getDescriptionId());
    }
    
    public static Builder builder(Block block) {
        return new Builder(block.getDescriptionId());
    }
    
    public static Builder builder(String category, String path) {
        return new Builder(category + "." + CreepyCreepers.MOD_ID + "." + path);
    }
    
    public static class Builder {
        private final String key;
        private final Map<String, String> localeMap;
        
        private Builder(String key) {
            this.key = key;
            this.localeMap = new HashMap<>();
        }
        
        public Builder addLocale(Locale locale, String translation) {
            this.localeMap.put(locale.get(), translation);
            return this;
        }
        
        public Translation build() {
            return new Translation(key, localeMap);
        }
    }
    
    public enum Locale {
        EN_US, PT_BR;
        
        public String get() { return this.name().toLowerCase(); }
    }
}
