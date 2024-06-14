package dev.nertzhul.creepycreepers.fabric.network;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricClientNetworkManager {
    public static void registerReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(FakeExplosionPacket.TYPE, new ClientMessageHandler<>());
    }
}
