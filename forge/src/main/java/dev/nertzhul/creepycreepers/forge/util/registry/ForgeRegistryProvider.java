package dev.nertzhul.creepycreepers.forge.util.registry;

import dev.nertzhul.creepycreepers.util.registry.RegistryEntries;
import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;

public class ForgeRegistryProvider<T> implements RegistryProvider<T> {
    private final String modId;
    private final DeferredRegister<T> register;
    private final RegistryEntries<T> entries = new RegistryEntries<>();
    
    public ForgeRegistryProvider(ResourceKey<? extends Registry<T>> pResourceKey, String pModId) {
        this.register = DeferredRegister.create(pResourceKey, pModId);
        this.modId = pModId;
    }
    
    @Override
    public void register() {
        this.register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
    @Override
    public <I extends T> RegistryEntry<I> register(String pName, Supplier<? extends I> pSupplier) {
        return this.entries.add(new ForgeRegistryEntry<>(this.register.register(pName, pSupplier)));
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
