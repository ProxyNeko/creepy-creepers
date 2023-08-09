package dev.nertzhul.creepycreepers.forge.platform.services;

import dev.nertzhul.creepycreepers.platform.services.PlatformHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements PlatformHelper {
    @Override
    public Platform platform() {
        return Platform.FORGE;
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
