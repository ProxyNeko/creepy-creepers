package dev.nertzhul.creepycreepers.network;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;

public class ClientboundMessageHandler {
    public static void handle(Minecraft minecraft, Player player, FakeExplosionPacket pPacket) {
        Explosion explosion = new Explosion(minecraft.level, null, pPacket.x(), pPacket.y(), pPacket.z(), pPacket.power(), pPacket.toBlow(),
            Explosion.BlockInteraction.KEEP, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
        
        explosion.finalizeExplosion(pPacket.particles());
        minecraft.player.setDeltaMovement(player.getDeltaMovement().add(pPacket.knockbackX(), pPacket.knockbackY(), pPacket.knockbackZ()));
    }
}
