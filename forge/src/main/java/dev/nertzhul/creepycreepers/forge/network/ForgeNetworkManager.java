package dev.nertzhul.creepycreepers.forge.network;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class ForgeNetworkManager {
    private static final String version = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        CreepyCreepers.resource("main_channel"), () -> version, version::equals, version::equals);
    
    private static int index = 0;
    
    public static void registerMessages() {
        register(FakeExplosionPacket.class, FakeExplosionPacket::new, NetworkDirection.PLAY_TO_CLIENT);
    }
    
    private static <T extends Message> void register(Class<T> clazz, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
        INSTANCE.messageBuilder(clazz, index++, direction)
            .encoder(T::encode).decoder(factory).consumerMainThread((packet, contextSupplier) -> {
                NetworkEvent.Context ctx = contextSupplier.get();
                ctx.enqueueWork(packet::handle);
                ctx.setPacketHandled(true);
            }).add();
    }
}
