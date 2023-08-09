package dev.nertzhul.creepycreepers.fabric.platform.services;

import dev.nertzhul.creepycreepers.fabric.util.registry.FabricRegistryProvider;
import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricRegistryFactory implements RegistryFactory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> pResourceKey, String pModId) {
        return new FabricRegistryProvider<>(pModId, pResourceKey);
    }
    
    @Override
    public <T> RegistryProvider<T> create(Registry<T> pRegistry, String pModId) {
        return new FabricRegistryProvider<>(pModId, pRegistry);
    }
}
