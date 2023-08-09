package dev.nertzhul.creepycreepers.quilt.platform.services;

import dev.nertzhul.creepycreepers.platform.services.PlatformHelper;
import net.fabricmc.api.EnvType;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;

public class QuiltPlatformHelper implements PlatformHelper {
    @Override
    public Platform platform() {
        return Platform.QUILT;
    }
    
    @Override
    public PhysicalSide physicalSide() {
        return MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT ? PhysicalSide.CLIENT : PhysicalSide.DEDICATED_SERVER;
    }
    
    @Override
    public boolean isModLoaded(String pModId) {
        return QuiltLoader.isModLoaded(pModId);
    }
    
    @Override
    public boolean isDevelopmentEnvironment() {
        return QuiltLoader.isDevelopmentEnvironment();
    }
}
