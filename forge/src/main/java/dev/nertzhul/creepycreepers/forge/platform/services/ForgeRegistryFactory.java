package dev.nertzhul.creepycreepers.forge.platform.services;

import dev.nertzhul.creepycreepers.forge.util.registry.ForgeRegistryProvider;
import dev.nertzhul.creepycreepers.platform.services.RegistryFactory;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

import java.util.Optional;

public class ForgeRegistryFactory implements RegistryFactory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> pResourceKey, String pModId) {
        Optional<? extends ModContainer> container = ModList.get().getModContainerById(pModId);
        if (container.isPresent() && container.get() instanceof FMLModContainer) {
            return new ForgeRegistryProvider<>(pResourceKey, pModId);
        }
        throw new NullPointerException("Cannot find mod container for id: " + pModId);
    }
}
