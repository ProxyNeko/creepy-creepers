package dev.nertzhul.creepycreepers.fabric.platform.services;

import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import dev.nertzhul.creepycreepers.util.registry.RegistryObject;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

public class FabricRegistryFactory implements RegistryFactory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return new Provider<>(modId, resourceKey);
    }
    
    @Override
    public <T> RegistryProvider<T> create(Registry<T> registry, String modId) {
        return new Provider<>(modId, registry);
    }
    
    @SuppressWarnings({ "unchecked" })
    private static class Provider<T> implements RegistryProvider<T> {
        private final String modId;
        private final Registry<T> registry;
        
        private final Set<RegistryObject<T>> entries = new ObjectOpenHashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(this.entries);
        
        private Provider(String modId, ResourceKey<? extends Registry<T>> key) {
            this.modId = modId;
            final var reg = BuiltInRegistries.REGISTRY.get(key.location());
            if (reg == null) {
                throw new RuntimeException("Registry with name " + key.location() + " was not found!");
            }
            this.registry = (Registry<T>) reg;
        }
        
        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }
        
        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var rl = ResourceLocation.fromNamespaceAndPath(this.modId, name);
            final var obj = Registry.register(this.registry, rl, supplier.get());
            
            final var ro = new RegistryObject<I>() {
                final ResourceKey<I> key = ResourceKey.create((ResourceKey<? extends Registry<I>>) Provider.this.registry.key(), rl);
                
                @Override
                public ResourceKey<I> resourceKey() {
                    return this.key;
                }
                
                @Override
                public ResourceLocation id() {
                    return rl;
                }
                
                @Override
                public I get() {
                    return obj;
                }
                
                @Override
                public Holder<I> asHolder() {
                    return (Holder<I>) Provider.this.registry.getHolder((ResourceKey<T>) this.key).orElseThrow();
                }
            };
            
            this.entries.add((RegistryObject<T>) ro);
            return ro;
        }
        
        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return this.entriesView;
        }
        
        @Override
        public String getModId() {
            return this.modId;
        }
    }
}
