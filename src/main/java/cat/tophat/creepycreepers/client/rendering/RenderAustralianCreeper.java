package cat.tophat.creepycreepers.client.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;

import cat.tophat.creepycreepers.CreepyCreepers;
import cat.tophat.creepycreepers.client.models.AustralianCreeperModel;
import cat.tophat.creepycreepers.common.entities.AustralianCreeperEntity;
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
