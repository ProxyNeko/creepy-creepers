package cat.tophat.creepycreepers.client.rendering;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * A fully modifiable version of {@link CreeperRenderer}.
 * Modded creepers should extend this class regardless.
 * 
 * @param <T> A class that extends {@link Creeper}. Should be left generic.
 * @param <M> A class that extends {@link EntityModel}. Should be left generic.
 */
public class CreepyCreeperRenderer<T extends Creeper, M extends EntityModel<T>> extends MobRenderer<T, M> {

	private final ResourceLocation texture;

	/**
	 * Constructor for the renderer. Applies the charge layer using the default texture.
	 * 
	 * @param renderManager The manager for entity renders.
	 * @param modelCreator An abstract model creator to grab the default and charged creeper models if applicable.
	 * @param shadowSize The shadow size of the creeper.
	 * @param texture The texture location of the creeper.
	 */
	public CreepyCreeperRenderer(EntityRenderDispatcher renderManager, Function<Float, M> modelCreator, float shadowSize, ResourceLocation texture) {
		this(renderManager, modelCreator, shadowSize, texture, true);
	}
	
	/**
	 * Constructor for the renderer.
	 * 
	 * @param renderManager The manager for entity renders.
	 * @param modelCreator An abstract model creator to grab the default and charged creeper models if applicable.
	 * @param shadowSize The shadow size of the creeper.
	 * @param texture The texture location of the creeper.
	 * @param applyChargeLayer If the charge layer should be attached to the renderer. Uses the default texture.
	 */
	public CreepyCreeperRenderer(EntityRenderDispatcher renderManager, Function<Float, M> modelCreator, float shadowSize, ResourceLocation texture, boolean applyChargeLayer) {
		this(renderManager, modelCreator, shadowSize, texture, null, applyChargeLayer);
	}
	
	/**
	 * Constructor for the renderer.
	 * 
	 * @param renderManager The manager for entity renders.
	 * @param modelCreator An abstract model creator to grab the default and charged creeper models if applicable.
	 * @param shadowSize The shadow size of the creeper.
	 * @param texture The texture location of the creeper.
	 * @param chargeLayerTexture If not null, uses the texture for the charge layer. Default to vanilla charge texture.
	 * @param applyChargeLayer If the charge layer should be attached to the renderer.
	 */
	public CreepyCreeperRenderer(EntityRenderDispatcher renderManager, Function<Float, M> modelCreator, float shadowSize, ResourceLocation texture, @Nullable ResourceLocation chargeLayerTexture, boolean applyChargeLayer) {
		super(renderManager, modelCreator.apply(0.0f), shadowSize);
		this.texture = texture;
		if(applyChargeLayer) {
			if(chargeLayerTexture != null) this.addLayer(new CreepyCreeperChargeLayer<>(this, modelCreator.apply(2.0f), chargeLayerTexture));
			else this.addLayer(new CreepyCreeperChargeLayer<>(this, modelCreator.apply(2.0f)));
		}
	}

	@Override
	protected void scale(Creeper entitylivingbase, PoseStack matrixStack, float partialTickTime) {
		float f = entitylivingbase.getSwelling(partialTickTime);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f = f * f;
		f = f * f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		matrixStack.scale(f2, f3, f2);
	}

	@Override
	protected float getWhiteOverlayProgress(Creeper livingEntity, float partialTicks) {
		float f = livingEntity.getSwelling(partialTicks);
		return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return this.texture;
	}
}