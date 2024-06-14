package dev.nertzhul.creepycreepers.neoforge.platform.services;

import dev.nertzhul.creepycreepers.platform.services.PlatformHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements PlatformHelper {
    @Override
    public Platform platform() {
        return Platform.NEOFORGE;
    }
    
    @Override
    public PhysicalSide physicalSide() {
        return FMLEnvironment.dist == Dist.CLIENT ? PhysicalSide.CLIENT : PhysicalSide.DEDICATED_SERVER;
    }
    
    @Override
    public boolean isModLoaded(String pModId) {
        return ModList.get().isLoaded(pModId);
    }
    
    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}
