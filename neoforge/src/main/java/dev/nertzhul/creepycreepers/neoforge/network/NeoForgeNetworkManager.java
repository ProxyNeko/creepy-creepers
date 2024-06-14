package dev.nertzhul.creepycreepers.neoforge.network;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworkManager {
    public static void register(final RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(CreepyCreepers.MOD_ID);
        
        registrar.playToClient(FakeExplosionPacket.TYPE, FakeExplosionPacket.CODEC, NeoForgeNetworkManager::handler);
    }
    
    public static <T extends Message> void handler(T message, IPayloadContext context) {
        if (context.flow().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                final ServerPlayer sender = (ServerPlayer) context.player();
                message.handleServer(sender.level().getServer(), sender);
            });
        } else {
            context.enqueueWork(() -> ClientMessageHandler.handleClient(message, context));
        }
    }
}
