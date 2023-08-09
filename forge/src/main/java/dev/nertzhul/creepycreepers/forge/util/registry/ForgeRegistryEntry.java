package dev.nertzhul.creepycreepers.forge.util.registry;

import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class ForgeRegistryEntry<T> implements RegistryEntry<T> {
    private final RegistryObject<T> object;
    
    public ForgeRegistryEntry(RegistryObject<T> pObject) {
        this.object = pObject;
    }
    
    @Override
    public ResourceKey<T> resourceKey() {
        return this.object.getKey();
    }
    
    @Override
    public ResourceLocation id() {
        return this.object.getId();
    }
    
    @Override
    public T get() {
        return this.object.get();
    }
    
    @Override
    public Holder<T> holder() {
        return this.object.getHolder().orElseThrow();
    }
}
