package cat.tophat.creepycreepers.client.rendering;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.resources.ResourceLocation;

/**
 * A more compatible version of {@link net.minecraft.client.renderer.entity.layers.CreeperPowerLayer}.
 * Modded creepers should never have a reason to explicitly
 * reference this class. It is already attached to {@link CreepyCreeperRenderer}.
 * 
 * @param <T> A class that extends {@link Creeper}. Should be left generic.
 * @param <M> A class that extends {@link EntityModel}. Should be left generic.
 */
public class CreepyCreeperChargeLayer<T extends Creeper, M extends EntityModel<T>> extends EnergySwirlLayer<T, M> {

	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private final M model;
	private final ResourceLocation texture;
	
	/**
	 * Constructor for the layer. Uses the default texture.
	 * 
	 * @param renderer The renderer currently adding this layer.
	 * @param model The model to render the texture over.
	 */
	public CreepyCreeperChargeLayer(RenderLayerParent<T, M> renderer, M model) {
		this(renderer, model, LIGHTNING_TEXTURE);
	}
	
	/**
	 * Constructor for the layer.
	 * 
	 * @param renderer The renderer currently adding this layer.
	 * @param model The model to render the texture over.
	 * @param texture The texture to render over the model.
	 */
	public CreepyCreeperChargeLayer(RenderLayerParent<T, M> renderer, M model, ResourceLocation texture) {
		super(renderer);
		this.model = model;
		this.texture = texture;
	}

	@Override
	protected float xOffset(float partialTicks) {
		return partialTicks * 0.01f;
	}

	@Override
	protected ResourceLocation getTextureLocation() {
		return this.texture;
	}

	@Override
	protected EntityModel<T> model() {
		return this.model;
	}

}