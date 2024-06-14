package dev.nertzhul.creepycreepers.fabric.network;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class FabricNetworkManager {
    public static void registerReceivers() {
    }
    
    public static void registerMessages() {
        // To Client
        PayloadTypeRegistry.playS2C().register(FakeExplosionPacket.TYPE, FakeExplosionPacket.CODEC);
    }
}
