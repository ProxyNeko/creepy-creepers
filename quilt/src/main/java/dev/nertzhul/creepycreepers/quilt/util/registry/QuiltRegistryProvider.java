package dev.nertzhul.creepycreepers.quilt.util.registry;

import dev.nertzhul.creepycreepers.util.registry.RegistryEntries;
import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.function.Supplier;

public class QuiltRegistryProvider<T> implements RegistryProvider<T> {
    private final String modId;
    private final Registry<T> registry;
    
    private final RegistryEntries<T> entries = new RegistryEntries<>();
    
    public QuiltRegistryProvider(String pModId, Registry<T> pRegistry) {
        this.modId = pModId;
        this.registry = pRegistry;
    }
    
    public QuiltRegistryProvider(String pModId, ResourceKey<? extends Registry<T>> pResourceKey) {
        this.modId = pModId;
        
        final Registry<?> reg = BuiltInRegistries.REGISTRY.get(pResourceKey.location());
        if (reg == null) {
            throw new RuntimeException("Could not find registry with name " + pResourceKey.location());
        }
        this.registry = (Registry<T>) reg;
    }
    
    @Override
    public void register() { /* NO-OP */ }
    
    @Override
    public <I extends T> RegistryEntry<I> register(String pName, Supplier<? extends I> pSupplier) {
        final ResourceLocation resource = new ResourceLocation(this.modId, pName);
        final I object = Registry.register(this.registry, resource, pSupplier.get());
        
        final RegistryEntry<I> entry = new RegistryEntry<>() {
            final ResourceKey<I> resourceKey = ResourceKey.create((ResourceKey<? extends Registry<I>>) QuiltRegistryProvider.this.registry.key(), resource);
            
            @Override
            public ResourceKey<I> resourceKey() {
                return this.resourceKey;
            }
            
            @Override
            public ResourceLocation id() {
                return resource;
            }
            
            @Override
            public I get() {
                return object;
            }
            
            @Override
            public Holder<I> holder() {
                return (Holder<I>) QuiltRegistryProvider.this.registry.getHolder((ResourceKey<T>) this.resourceKey).orElseThrow();
            }
        };
        return this.entries.add(entry);
    }
    
    @Override
    public Collection<RegistryEntry<T>> getEntries() {
        return this.entries.getEntries();
    }
    
    @Override
    public String getModId() {
        return this.modId;
    }
}
