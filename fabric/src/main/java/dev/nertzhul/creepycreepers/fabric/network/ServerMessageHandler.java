package dev.nertzhul.creepycreepers.fabric.network;

import dev.nertzhul.creepycreepers.network.base.Message;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerMessageHandler<T extends Message> implements ServerPlayNetworking.PlayPayloadHandler<T> {
    public ServerMessageHandler() {
    }
    
    @Override
    public void receive(T payload, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            payload.handleServer(context.player().getServer(), context.player());
        });
    }
}
