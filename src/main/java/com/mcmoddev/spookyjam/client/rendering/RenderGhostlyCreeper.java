package com.mcmoddev.spookyjam.client.rendering;

import com.mcmoddev.spookyjam.CreepyCreepers;
import com.mcmoddev.spookyjam.client.models.GhostlyCreeperModel;
import com.mcmoddev.spookyjam.common.entities.GhostlyCreeperEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderGhostlyCreeper extends LivingRenderer<GhostlyCreeperEntity, GhostlyCreeperModel> {

    private static final ResourceLocation resourceLocation = new ResourceLocation(CreepyCreepers.MODID, "textures/entity/ghostly_creeper.png");

    public RenderGhostlyCreeper(EntityRendererManager rendererManager) {
        super(rendererManager, new GhostlyCreeperModel(), 0.4F);
    }

    @Override
    protected void preRenderCallback(GhostlyCreeperEntity entity, float partialTickTime) {
        if (entityModel.isChild) {
            GlStateManager.scaled(0.65D, 0.65D, 0.65D);
        } else {
            GlStateManager.scaled(1.0D, 1.0D, 1.0D);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(GhostlyCreeperEntity entity) {
        return resourceLocation;
    }
}
