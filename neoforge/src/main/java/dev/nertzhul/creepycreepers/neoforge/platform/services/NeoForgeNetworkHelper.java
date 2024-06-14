package dev.nertzhul.creepycreepers.neoforge.platform.services;

import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.platform.services.NetworkHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHelper implements NetworkHelper {
    @Override
    public <T extends Message> void sendTo(ServerPlayer pPlayer, T pMessage) {
        PacketDistributor.sendToPlayer(pPlayer, pMessage);
    }
    
    @Override
    public <T extends Message> void sendToServer(T pMessage) {
        PacketDistributor.sendToServer(pMessage);
        
    }
    
    @Override
    public <T extends Message> void sendToTracking(ServerLevel pLevel, BlockPos pPos, T pMessage) {
        PacketDistributor.sendToPlayersTrackingChunk(pLevel, new ChunkPos(pPos), pMessage);
    }
    
    @Override
    public <T extends Message> void sendToTracking(Entity pEntity, T pMessage) {
        PacketDistributor.sendToPlayersTrackingEntity(pEntity, pMessage);
    }
}
