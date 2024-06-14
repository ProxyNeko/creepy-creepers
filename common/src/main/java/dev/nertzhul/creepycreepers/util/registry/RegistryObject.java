package dev.nertzhul.creepycreepers.util.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * Represents a lazy wrapper for a registry object.
 */
public interface RegistryObject<T> extends Supplier<T> {
    /**
     * Gets the {@link ResourceKey} of the registry of the object wrapped.
     *
     * @return the {@link ResourceKey} of the registry
     */
    ResourceKey<T> resourceKey();
    
    /**
     * Gets the id of the object.
     *
     * @return the id of the object
     */
    ResourceLocation id();
    
    /**
     * Gets the object behind this wrapper. Calling this method too early
     * might result in crashes.
     *
     * @return the object behind this wrapper
     */
    @Override
    T get();
    
    /**
     * Gets this object wrapped in a vanilla {@link Holder}.
     *
     * @return the holder
     */
    Holder<T> asHolder();
}
