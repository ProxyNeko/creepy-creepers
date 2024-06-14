package dev.nertzhul.creepycreepers.forge.platform.services;

import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import dev.nertzhul.creepycreepers.util.registry.RegistryObject;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

public class ForgeRegistryFactory implements RegistryFactory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        final var container = ModList.get().getModContainerById(modId);
        if (container.isEmpty()) {
            throw new IllegalArgumentException("Mod with id " + modId + " is not present.");
        }
        
        if (container.get() instanceof FMLModContainer modContainer) {
            final var register = DeferredRegister.create(resourceKey, modId);
            register.register(modContainer.getEventBus());
            return new Provider<>(modId, register);
        } else {
            throw new IllegalArgumentException("Mod with id " + modId + " is not a FMLModContainer.");
        }
    }
    
    private static class Provider<T> implements RegistryProvider<T> {
        private final String modId;
        private final DeferredRegister<T> register;
        
        private final Set<RegistryObject<T>> entries = new ObjectOpenHashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(this.entries);
        
        public Provider(String modId, DeferredRegister<T> register) {
            this.modId = modId;
            this.register = register;
        }
        
        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var obj = this.register.<I>register(name, supplier);
            final var ro = new RegistryObject<I>() {
                @Override
                public ResourceKey<I> resourceKey() {
                    return obj.getKey();
                }
                
                @Override
                public ResourceLocation id() {
                    return obj.getId();
                }
                
                @Override
                public I get() {
                    return obj.get();
                }
                
                @Override
                public Holder<I> asHolder() {
                    return obj.getHolder().orElseThrow();
                }
            };
            this.entries.add((RegistryObject<T>) ro);
            return ro;
        }
        
        @Override
        public String getModId() {
            return modId;
        }
        
        @Override
        public Set<RegistryObject<T>> getEntries() {
            return this.entriesView;
        }
    }
}
