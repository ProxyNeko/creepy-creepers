package dev.nertzhul.creepycreepers.util;

import dev.nertzhul.creepycreepers.network.clientbound.FakeExplosionPacket;
import dev.nertzhul.creepycreepers.platform.Services;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class ExplosionUtil {
    private ExplosionUtil() { }
    
    public static void fakeExplosion(ServerLevel pLevel, @Nullable Entity pSource, double pX, double pY, double pZ, float pRadius, boolean particles) {
        Explosion explosion = pLevel.explode(pSource, null, null, pX, pY,  pZ, pRadius, false, Level.ExplosionInteraction.NONE, false);
        explosion.clearToBlow();
        
        pLevel.players().forEach(serverPlayer ->
            Services.NETWORK.sendTo(serverPlayer, new FakeExplosionPacket(pX,  pY,  pZ,  pRadius, explosion.getToBlow(), explosion.getHitPlayers().get(serverPlayer), particles))
        );
    }
}
