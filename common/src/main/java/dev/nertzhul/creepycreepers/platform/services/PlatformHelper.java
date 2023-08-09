package dev.nertzhul.creepycreepers.platform.services;

public interface PlatformHelper {
    Platform platform();
    
    PhysicalSide physicalSide();
    
    boolean isModLoaded(String pModId);
    
    boolean isDevelopmentEnvironment();
    
    enum Platform {
        FORGE, FABRIC, QUILT
    }
    
    enum PhysicalSide {
        CLIENT, DEDICATED_SERVER
    }
}
