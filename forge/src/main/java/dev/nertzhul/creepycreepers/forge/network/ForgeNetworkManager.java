package dev.nertzhul.creepycreepers.forge.network;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.forge.client.util.DistHelper;
import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ForgeNetworkManager {
    public static final SimpleChannel INSTANCE = ChannelBuilder
        .named(CreepyCreepers.resource("main"))
        .clientAcceptedVersions((a, b) -> true)
        .serverAcceptedVersions((a, b) -> true)
        .networkProtocolVersion(1)
        .simpleChannel();
    
    private static int index = 0;
    
    public static void registerMessages() {
        INSTANCE.messageBuilder(FakeExplosionPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(encoder(FakeExplosionPacket.CODEC))
            .decoder(decoder(FakeExplosionPacket.CODEC))
            .consumerNetworkThread((BiConsumer<FakeExplosionPacket, CustomPayloadEvent.Context>) ForgeNetworkManager::handler)
            .add();
    }
    
    public static <MSG> BiConsumer<MSG, RegistryFriendlyByteBuf> encoder(StreamCodec<RegistryFriendlyByteBuf, MSG> codec) {
        return (msg, buf) -> {
            var rBuf = RegistryFriendlyByteBuf.decorator(getRegistryAccess()).apply(buf);
            codec.encode(rBuf, msg);
        };
    }
    
    public static <MSG> Function<RegistryFriendlyByteBuf, MSG> decoder(StreamCodec<RegistryFriendlyByteBuf, MSG> codec) {
        return buf -> {
            var rBuf = RegistryFriendlyByteBuf.decorator(getRegistryAccess()).apply(buf);
            return codec.decode(rBuf);
        };
    }
    
    public static <T extends Message> void handler(T message, CustomPayloadEvent.Context context) {
        if (context.isServerSide()) {
            context.enqueueWork(() -> {
                final ServerPlayer sender = context.getSender();
                message.handleServer(sender.level().getServer(), sender);
            });
        } else {
            context.enqueueWork(() -> ClientMessageHandler.handleClient(message, context));
        }
    }
    
    public static RegistryAccess getRegistryAccess() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return DistHelper.getRegistryAccess();
        }
        return ServerLifecycleHooks.getCurrentServer().registryAccess();
    }
}
