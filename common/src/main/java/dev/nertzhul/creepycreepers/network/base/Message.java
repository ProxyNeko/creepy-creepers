package dev.nertzhul.creepycreepers.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface Message {
    ResourceLocation getId();
    
    void encode(FriendlyByteBuf pBuf);
    
    void handle();
}
