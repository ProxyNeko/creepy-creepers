package dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCreeperPowerLayer;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GhostlyCreeperRenderer extends CcCreeperRenderer<GhostlyCreeper, GhostlyCreeperModel> {
    private static final ResourceLocation TEXTURE = CreepyCreepers.resource("textures/entity/ghostly_creeper.png");
    
    public GhostlyCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostlyCreeperModel(context.bakeLayer(GhostlyCreeperModel.LAYER)), 0.0F);
        this.addLayer(new CcCreeperPowerLayer<>(this, new GhostlyCreeperModel(context.bakeLayer(ModelLayers.CREEPER_ARMOR))));
    }
    @Override
    public void render(@NotNull GhostlyCreeper entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.render(entity, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
    }
    
    @Override
    @NotNull
    public ResourceLocation getTextureLocation(GhostlyCreeper entity) {
        return TEXTURE;
    }
}
