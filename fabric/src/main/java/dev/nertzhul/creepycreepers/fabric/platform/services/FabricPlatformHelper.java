package dev.nertzhul.creepycreepers.fabric.platform.services;

import dev.nertzhul.creepycreepers.platform.services.PlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Platform platform() {
        return Platform.FORGE;
    }
    
    @Override
    public PhysicalSide physicalSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? PhysicalSide.CLIENT : PhysicalSide.DEDICATED_SERVER;
    }
    
    @Override
    public boolean isModLoaded(String pModId) {
        return FabricLoader.getInstance().isModLoaded(pModId);
    }
    
    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
