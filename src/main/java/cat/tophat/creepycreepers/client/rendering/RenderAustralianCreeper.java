package cat.tophat.creepycreepers.client.rendering;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.client.models.AustralianCreeperModel;
import cat.tophat.creepycreepers.common.entities.AustralianCreeperEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderAustralianCreeper extends MobRenderer<AustralianCreeperEntity, AustralianCreeperModel> {

    private static final ResourceLocation resourceLocation = new ResourceLocation(CreepyCreepers.MOD_ID,
            "textures/entity/australian_creeper.png");

    public RenderAustralianCreeper(EntityRendererManager rendererManager) {
        super(rendererManager, new AustralianCreeperModel(), 0.4F);
        shadowSize = 0;
    }

    @Override
    protected void preRenderCallback(AustralianCreeperEntity entity, float partialTickTime) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        float f = entity.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scalef(f2, f3, f2);
    }

    @Override
    protected boolean canRenderName(AustralianCreeperEntity entity) {
        return entity.hasCustomName();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AustralianCreeperEntity entity) {
        return resourceLocation;
    }
}
