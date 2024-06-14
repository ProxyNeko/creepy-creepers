package dev.nertzhul.creepycreepers.platform;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.platform.services.NetworkHelper;
import dev.nertzhul.creepycreepers.platform.services.PlatformHelper;

import java.util.ServiceLoader;

/**
 * Service loaders are a built-in Java feature that allows us to locate implementations of an interface that vary from one
 * environment to another.
 * <p>
 * In the context of MultiLoader, we use this feature to access a mock API in the common code that
 * is swapped out for the platform-specific implementation at runtime.
 */
public class Services {
    public static final PlatformHelper PLATFORM = load(PlatformHelper.class);
    public static final NetworkHelper NETWORK = load(NetworkHelper.class);
    
    /**
     * This code is used to load a service for the current environment. Your implementation of the service must be defined
     * manually by including a text file in META-INF/services named with the fully qualified class name of the service.
     * <p>
     * Inside the file, you should write the fully qualified class name of the implementation to load for the platform.
     * For example, our file on Forge points to ForgePlatformHelper while Fabric points to FabricPlatformHelper.
     */
    public static <T> T load(Class<T> pService) {
        T service = ServiceLoader.load(pService).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + pService.getName()));
        CreepyCreepers.LOGGER.debug("Loaded {} for service {}.", service, pService);
        return service;
    }
}
