package dev.nertzhul.creepycreepers.network.clientbound;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.network.ClientboundMessageHandler;
import dev.nertzhul.creepycreepers.network.base.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * Copy of {@link net.minecraft.network.protocol.game.ClientboundExplodePacket ClientboundExplodePacket} that doesn't display any particles.
 */
public final class FakeExplosionPacket implements Message {
    public static final ResourceLocation ID = CreepyCreepers.resource("fake_explosion_packet");
    
    private final double x;
    private final double y;
    private final double z;
    private final float power;
    private final List<BlockPos> toBlow;
    private final float knockbackX;
    private final float knockbackY;
    private final float knockbackZ;
    
    public FakeExplosionPacket(double x, double y, double z, float power, List<BlockPos> toBlow, @Nullable Vec3 knockback) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = power;
        this.toBlow = toBlow;
        if (knockback != null) {
            this.knockbackX = (float) knockback.x;
            this.knockbackY = (float) knockback.y;
            this.knockbackZ = (float) knockback.z;
        } else {
            this.knockbackX = 0.0F;
            this.knockbackY = 0.0F;
            this.knockbackZ = 0.0F;
        }
    }
    
    public FakeExplosionPacket(FriendlyByteBuf pBuf) {
        this.x = pBuf.readDouble();
        this.y = pBuf.readDouble();
        this.z = pBuf.readDouble();
        this.power = pBuf.readFloat();
        int i = Mth.floor(this.x);
        int j = Mth.floor(this.y);
        int k = Mth.floor(this.z);
        this.toBlow = pBuf.readList((friendlyByteBuf) -> {
            int l = friendlyByteBuf.readByte() + i;
            int m = friendlyByteBuf.readByte() + j;
            int n = friendlyByteBuf.readByte() + k;
            return new BlockPos(l, m, n);
        });
        this.knockbackX = pBuf.readFloat();
        this.knockbackY = pBuf.readFloat();
        this.knockbackZ = pBuf.readFloat();
    }
    
    @Override
    public void encode(FriendlyByteBuf pBuf) {
        pBuf.writeDouble(this.x);
        pBuf.writeDouble(this.y);
        pBuf.writeDouble(this.z);
        pBuf.writeFloat(this.power);
        int i = Mth.floor(this.x);
        int j = Mth.floor(this.y);
        int k = Mth.floor(this.z);
        pBuf.writeCollection(this.toBlow, (friendlyByteBuf, blockPos) -> {
            int l = blockPos.getX() - i;
            int m = blockPos.getY() - j;
            int n = blockPos.getZ() - k;
            friendlyByteBuf.writeByte(l);
            friendlyByteBuf.writeByte(m);
            friendlyByteBuf.writeByte(n);
        });
        pBuf.writeFloat(this.knockbackX);
        pBuf.writeFloat(this.knockbackY);
        pBuf.writeFloat(this.knockbackZ);
    }
    
    @Override
    public void handle() {
        ClientboundMessageHandler.handle(this);
    }
    
    @Override
    public ResourceLocation getId() {
        return ID;
    }
    
    public double x() { return this.x; }
    
    public double y() { return this.y; }
    
    public double z() { return this.z; }
    
    public float power() { return this.power; }
    
    public List<BlockPos> toBlow() { return this.toBlow; }
    
    public float knockbackX() {
        return this.knockbackX;
    }
    
    public float knockbackY() {
        return this.knockbackY;
    }
    
    public float knockbackZ() {
        return this.knockbackZ;
    }
}
