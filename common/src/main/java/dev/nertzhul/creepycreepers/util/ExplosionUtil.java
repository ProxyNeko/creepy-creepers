package dev.nertzhul.creepycreepers.util;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import dev.nertzhul.creepycreepers.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class ExplosionUtil {
    private ExplosionUtil() { }
    
    public static void fakeExplosion(ServerLevel level, @Nullable Entity source, double x, double y, double z, float radius, boolean particles) {
        Explosion explosion = level.explode(source, null, null, x, y,  z, radius, false,
            Level.ExplosionInteraction.NONE, false, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
        explosion.clearToBlow();
        
        level.players().forEach(serverPlayer ->
            Services.NETWORK.sendTo(serverPlayer, new FakeExplosionPacket(x,  y,  z,  radius, explosion.getToBlow(), explosion.getHitPlayers().get(serverPlayer), particles))
        );
    }
}
