package dev.nertzhul.creepycreepers.fabric.platform.services;

import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.platform.services.NetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;

public class FabricNetworkHelper implements NetworkHelper {
    @Override
    public <T extends Message> void sendTo(ServerPlayer pPlayer, T pMessage) {
        ServerPlayNetworking.send(pPlayer, pMessage);
    }
    
    @Override
    public <T extends Message> void sendToServer(T pMessage) {
        ClientPlayNetworking.send(pMessage);
    }
    
    @Override
    public <T extends Message> void sendToTracking(ServerLevel pLevel, BlockPos pPos, T pMessage) {
        LevelChunk chunk = pLevel.getChunkAt(pPos);
        pLevel.getChunkSource().chunkMap.getPlayers(chunk.getPos(), false).forEach(serverPlayer -> {
            ServerPlayNetworking.send(serverPlayer, pMessage);
        });
    }
    
    @Override
    public <T extends Message> void sendToTracking(Entity pEntity, T pMessage) {
        ((ServerChunkCache) pEntity.getCommandSenderWorld().getChunkSource())
            .broadcast(pEntity, ServerPlayNetworking.createS2CPacket(pMessage));
    }
}
