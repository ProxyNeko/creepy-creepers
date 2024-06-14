package dev.nertzhul.creepycreepers.neoforge.network;

import dev.nertzhul.creepycreepers.network.base.Message;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientMessageHandler {
    public static <T extends Message> void handleClient(T message, IPayloadContext ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        message.handleClient(minecraft, minecraft.player);
    }
}
