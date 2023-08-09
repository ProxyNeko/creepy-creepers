package dev.nertzhul.creepycreepers.quilt.platform.services;

import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.platform.services.NetworkHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class QuiltNetworkHelper implements NetworkHelper {
    @Override
    public <T extends Message> void sendTo(ServerPlayer pPlayer, T pMessage) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        pMessage.encode(buf);
        ServerPlayNetworking.send(pPlayer, pMessage.getId(), buf);
    }
    
    @Override
    public <T extends Message> void sendToServer(T pMessage) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        pMessage.encode(buf);
        ClientPlayNetworking.send(pMessage.getId(), buf);
    }
    
    @Override
    public <T extends Message> void sendToTracking(ServerLevel pLevel, BlockPos pPos, T pMessage) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        pMessage.encode(byteBuf);
        
        LevelChunk chunk = pLevel.getChunkAt(pPos);
        pLevel.getChunkSource().chunkMap.getPlayers(chunk.getPos(), false).forEach(serverPlayer -> {
            ServerPlayNetworking.send(serverPlayer, pMessage.getId(), byteBuf);
        });
    }
    
    @Override
    public <T extends Message> void sendToTracking(Entity pEntity, T pMessage) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        pMessage.encode(byteBuf);
        
        ((ServerChunkCache) pEntity.getCommandSenderWorld().getChunkSource())
            .broadcast(pEntity, ServerPlayNetworking.createS2CPacket(pMessage.getId(), byteBuf));
    }
}
