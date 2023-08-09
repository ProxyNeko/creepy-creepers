package dev.nertzhul.creepycreepers.fabric.network;

import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class FabricNetworkManager {
    public static void registerServerMessages() {
    }
    
    public static void registerClientMessages() {
        registerClientMessage(FakeExplosionPacket.ID, FakeExplosionPacket::new);
    }
    
    private static <T extends Message> void registerClientMessage(ResourceLocation id, Function<FriendlyByteBuf, T> decoder) {
        ClientPlayNetworking.registerGlobalReceiver(id, (client, handler, buf, responseSender) -> {
            T packet = decoder.apply(buf);
            client.execute(packet::handle);
        });
    }
}
