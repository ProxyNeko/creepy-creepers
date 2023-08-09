package dev.nertzhul.creepycreepers.platform.services;

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
     * Creates a {@link RegistryProvider}.
     *
     * @param pResourceKey the {@link ResourceKey} of the registry to create this provider for
     * @param pModId       the mod id for which the provider will register objects
     * @param <T>         the type of the provider
     * @return the provider
     */
    <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> pResourceKey, String pModId);
    
    /**
     * Creates a {@link RegistryProvider}.
     *
     * @param pRegistry the {@link Registry} to create this provider for
     * @param pModId    the mod id for which the provider will register objects
     * @param <T>      the type of the provider
     * @return the provider
     */
    default <T> RegistryProvider<T> create(Registry<T> pRegistry, String pModId) {
        return this.create(pRegistry.key(), pModId);
    }
}
