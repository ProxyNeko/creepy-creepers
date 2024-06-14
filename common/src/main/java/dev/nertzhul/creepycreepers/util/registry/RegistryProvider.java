package dev.nertzhul.creepycreepers.util.registry;

import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface RegistryProvider<T> {
    /**
     * Creates a provider for specified {@code modId} and {@code resourceKey}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field to
     * the {@link RegistryFactory#INSTANCE factory} creating multiple providers for the same resource key and mod id.
     *
     * @param resourceKey the {@link ResourceKey} of the registry of the provider
     * @param modId       the mod id that the provider will register objects for
     * @param <T>         the type of the provider
     * @return the provider
     */
    static <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return RegistryFactory.INSTANCE.create(resourceKey, modId);
    }
    
    /**
     * Creates a provider for specified {@code modId} and {@code registry}. <br>
     * It is <i>recommended</i> to store the resulted provider in a {@code static final} field to
     * the {@link RegistryFactory#INSTANCE factory} creating multiple providers for the same resource key and mod id.
     *
     * @param registry the {@link Registry} of the provider
     * @param modId    the mod id that the provider will register objects for
     * @param <T>      the type of the provider
     * @return the provider
     */
    static <T> RegistryProvider<T> create(Registry<T> registry, String modId) {
        return RegistryFactory.INSTANCE.create(registry, modId);
    }
    
    /**
     * Registers an object.
     *
     * @param name     the name of the object
     * @param supplier a supplier of the object to register
     * @param <I>      the type of the object
     * @return a wrapper containing the lazy registered object. <strong>Calling {@link RegistryObject#get() get} too early
     * on the wrapper might result in crashes!</strong>
     */
    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);
    
    /**
     * Gets the mod id that this provider registers objects for.
     *
     * @return the mod id
     */
    String getModId();
    
    /**
     * Gets all the objects currently registered.
     *
     * @return an <strong>immutable</strong> view of all the objects currently registered
     */
    Collection<RegistryObject<T>> getEntries();
    
    /**
     * Gets a {@link Stream} of all the objects currently registered.
     */
    default Stream<RegistryObject<T>> stream() {
        return getEntries().stream();
    }
}
