package dev.nertzhul.creepycreepers.quilt.platform.services;

import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import dev.nertzhul.creepycreepers.quilt.util.registry.QuiltRegistryProvider;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class QuiltRegistryFactory implements RegistryFactory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> pResourceKey, String pModId) {
        return new QuiltRegistryProvider<>(pModId, pResourceKey);
    }
    
    @Override
    public <T> RegistryProvider<T> create(Registry<T> pRegistry, String pModId) {
        return new QuiltRegistryProvider<>(pModId, pRegistry);
    }
}
