package dev.nertzhul.creepycreepers.fabric.network;

import dev.nertzhul.creepycreepers.network.base.Message;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientMessageHandler<T extends Message> implements ClientPlayNetworking.PlayPayloadHandler<T> {
    public ClientMessageHandler() {
    }
    
    @Override
    public void receive(T payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            payload.handleClient(context.client(), context.player());
        });
    }
}
