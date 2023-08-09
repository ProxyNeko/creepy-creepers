package dev.nertzhul.creepycreepers.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;

public abstract class CcCreeperRenderer<T extends Creeper> extends MobRenderer<T, CcCreeperModel<T>> {
    public CcCreeperRenderer(EntityRendererProvider.Context context, CcCreeperModel<T> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
    
    @Override
    protected void scale(@NotNull T entity, @NotNull PoseStack poseStack, float partialTickTime) {
        float f = entity.getSwelling(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        
        poseStack.scale(f2, f3, f2);
    }
    
    @Override
    protected float getWhiteOverlayProgress(T livingEntity, float partialTicks) {
        float f = livingEntity.getSwelling(partialTicks);
        return f * 10.0F % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }
}
