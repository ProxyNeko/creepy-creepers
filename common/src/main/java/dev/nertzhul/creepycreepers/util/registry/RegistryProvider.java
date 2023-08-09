package dev.nertzhul.creepycreepers.util.registry;

import dev.nertzhul.creepycreepers.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface RegistryProvider<T> {
    /**
     * Creates a provider for specified {@code pModId} and {@code pResourceKey}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field to
     * the {@link Services#REGISTRY_FACTORY factory} creating multiple providers for the same resource key and mod id.
     *
     * @param pResourceKey the {@link ResourceKey} of the registry of the provider
     * @param pModId       the mod id that the provider will register objects for
     * @param <T>         the type of the provider
     * @return the provider
     */
    static <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> pResourceKey, String pModId) {
        return Services.REGISTRY_FACTORY.create(pResourceKey, pModId);
    }
    
    /**
     * Creates a provider for specified {@code pModId} and {@code pRegistry}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field to
     * the {@link Services#REGISTRY_FACTORY factory} creating multiple providers for the same resource key and mod id.
     *
     * @param pRegistry the {@link Registry} of the provider
     * @param pModId    the mod id that the provider will register objects for
     * @param <T>      the type of the provider
     * @return the provider
     */
    static <T> RegistryProvider<T> create(Registry<T> pRegistry, String pModId) {
        return Services.REGISTRY_FACTORY.create(pRegistry, pModId);
    }
    
    /**
     * Registers an object.
     *
     * @param pName     the name of the object
     * @param pSupplier a supplier of the object to register
     * @param <I>      the type of the object
     * @return a wrapper containing the lazy registered object. <strong>Calling {@link RegistryEntry#get() get} too early
     * on the wrapper might result in crashes!</strong>
     */
    <I extends T> RegistryEntry<I> register(String pName, Supplier<? extends I> pSupplier);
    
    /**
     * Gets all the objects currently registered.
     *
     * @return an <strong>immutable</strong> view of all the objects currently registered
     */
    Collection<RegistryEntry<T>> getEntries();
    
    /**
     * Gets a {@link Stream} of all the objects currently registered.
     */
    default Stream<RegistryEntry<T>> stream() {
        return getEntries().stream();
    }
    
    /**
     * Gets the mod id that this provider registers objects for.
     *
     * @return the mod id
     */
    String getModId();
    
    /**
     * Initializes the registry on the loader.
     */
    void register();
}
