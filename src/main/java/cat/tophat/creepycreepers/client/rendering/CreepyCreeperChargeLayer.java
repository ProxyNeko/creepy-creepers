package cat.tophat.creepycreepers.client.rendering;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

/**
 * A more compatible version of {@link CreeperChargeLayer}.
 * Modded creepers should never have a reason to explicitly
 * reference this class. It is already attached to {@link CreepyCreeperRenderer}.
 * 
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 * @param <M> A class that extends {@link EntityModel}. Should be left generic.
 */
public class CreepyCreeperChargeLayer<T extends CreeperEntity, M extends EntityModel<T>> extends EnergyLayer<T, M> {

	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private final M model;
	private final ResourceLocation texture;
	
	/**
	 * Constructor for the layer. Uses the default texture.
	 * 
	 * @param renderer The renderer currently adding this layer.
	 * @param model The model to render the texture over.
	 */
	public CreepyCreeperChargeLayer(IEntityRenderer<T, M> renderer, M model) {
		this(renderer, model, LIGHTNING_TEXTURE);
	}
	
	/**
	 * Constructor for the layer.
	 * 
	 * @param renderer The renderer currently adding this layer.
	 * @param model The model to render the texture over.
	 * @param texture The texture to render over the model.
	 */
	public CreepyCreeperChargeLayer(IEntityRenderer<T, M> renderer, M model, ResourceLocation texture) {
		super(renderer);
		this.model = model;
		this.texture = texture;
	}

	@Override
	protected float func_225634_a_(float partialTicks) {
		return partialTicks * 0.01f;
	}

	@Override
	protected ResourceLocation func_225633_a_() {
		return this.texture;
	}

	@Override
	protected EntityModel<T> func_225635_b_() {
		return this.model;
	}

}
