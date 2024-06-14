package dev.nertzhul.creepycreepers.forge.platform.services;

import dev.nertzhul.creepycreepers.forge.network.ForgeNetworkManager;
import dev.nertzhul.creepycreepers.network.base.Message;
import dev.nertzhul.creepycreepers.platform.services.NetworkHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;

public class ForgeNetworkHelper implements NetworkHelper {
    @Override
    public <T extends Message> void sendTo(ServerPlayer pPlayer, T pMessage) {
        ForgeNetworkManager.INSTANCE.send(pMessage, PacketDistributor.PLAYER.with(pPlayer));
    }
    
    @Override
    public <T extends Message> void sendToServer(T pMessage) {
        ForgeNetworkManager.INSTANCE.send(pMessage, PacketDistributor.SERVER.noArg());
    }
    
    @Override
    public <T extends Message> void sendToTracking(ServerLevel pLevel, BlockPos pPos, T pMessage) {
        ForgeNetworkManager.INSTANCE.send(pMessage, PacketDistributor.TRACKING_CHUNK.with(pLevel.getChunkAt(pPos)));
    }
    
    @Override
    public <T extends Message> void sendToTracking(Entity pEntity, T pMessage) {
        ForgeNetworkManager.INSTANCE.send(pMessage, PacketDistributor.TRACKING_ENTITY.with(pEntity));
    }
}
