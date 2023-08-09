package dev.nertzhul.creepycreepers.network;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Explosion;

public class ClientboundMessageHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    
    public static void handle(FakeExplosionPacket pPacket) {
        Explosion explosion = new Explosion(minecraft.level, null, pPacket.x(), pPacket.y(), pPacket.z(), pPacket.power(), pPacket.toBlow());
        explosion.finalizeExplosion(false);
        minecraft.player.setDeltaMovement(minecraft.player.getDeltaMovement().add(pPacket.knockbackX(), pPacket.knockbackY(), pPacket.knockbackZ()));
    }
}
