package dev.nertzhul.creepycreepers.forge.network;

import dev.nertzhul.creepycreepers.network.base.Message;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class ClientMessageHandler {
    public static <T extends Message> void handleClient(T message, CustomPayloadEvent.Context ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        message.handleClient(minecraft, minecraft.player);
    }
}
