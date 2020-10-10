package com.mcmoddev.creepycreepers.client.rendering;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import com.mcmoddev.creepycreepers.client.models.AustralianCreeperModel;
import com.mcmoddev.creepycreepers.common.entities.AustralianCreeperEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderAustralianCreeper extends MobRenderer<AustralianCreeperEntity, AustralianCreeperModel> {

	private static final ResourceLocation LOCATION = new ResourceLocation(CreepyCreepers.MOD_ID,
			"textures/entity/australian_creeper.png");

	public RenderAustralianCreeper(EntityRendererManager rendererManager) {
		super(rendererManager, new AustralianCreeperModel(), 0.0F);
	}

	@Override
	protected void preRenderCallback(AustralianCreeperEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
		float f = entity.getCreeperFlashIntensity(partialTickTime);
		float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		f = f * f;
		f = f * f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		matrixStackIn.scale(f2, f3, f2);
	}

	@Override
	protected boolean canRenderName(AustralianCreeperEntity entity) {
		return entity.hasCustomName();
	}

	@Override
	public ResourceLocation getEntityTexture(AustralianCreeperEntity entity) {
		return LOCATION;
	}
}
