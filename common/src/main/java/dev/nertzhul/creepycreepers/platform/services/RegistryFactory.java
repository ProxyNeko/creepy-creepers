package dev.nertzhul.creepycreepers.platform.services;

import dev.nertzhul.creepycreepers.platform.Services;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Factory class for {@link RegistryProvider registry providers}. <br>
 * This class is loaded using {@link java.util.ServiceLoader Service Loaders}, and only one
 * should exist per mod loader.
 */
public interface RegistryFactory {
    /**
     * The singleton instance of the {@link RegistryFactory}. This is different on each loader.
     */
    RegistryFactory INSTANCE = Services.load(RegistryFactory.class);
    
    /**
     * Creates a {@link RegistryProvider}.
     *
     * @param resourceKey the {@link ResourceKey} of the registry to create this provider for
     * @param modId       the mod id for which the provider will register objects
     * @param <T>         the type of the provider
     *
     * @return the provider
     */
    <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId);
    
    /**
     * Creates a {@link RegistryProvider}.
     *
     * @param registry the {@link Registry} to create this provider for
     * @param modId    the mod id for which the provider will register objects
     * @param <T>      the type of the provider
     *
     * @return the provider
     */
    default <T> RegistryProvider<T> create(Registry<T> registry, String modId) {
        return this.create(registry.key(), modId);
    }
}
