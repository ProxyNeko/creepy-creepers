package dev.nertzhul.creepycreepers.platform.services;

import dev.nertzhul.creepycreepers.network.base.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public interface NetworkHelper {
    <T extends Message> void sendTo(ServerPlayer pPlayer, T pMessage);
    
    <T extends Message> void sendToServer(T pMessage);
    
    <T extends Message> void sendToTracking(ServerLevel pLevel, BlockPos pPos, T pMessage);
    
    <T extends Message> void sendToTracking(Entity pEntity, T pMessage);
}
