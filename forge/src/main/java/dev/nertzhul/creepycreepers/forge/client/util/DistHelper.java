package dev.nertzhul.creepycreepers.forge.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;

public class DistHelper {
    public static RegistryAccess getRegistryAccess() {
        return Minecraft.getInstance().level.registryAccess();
    }
}
