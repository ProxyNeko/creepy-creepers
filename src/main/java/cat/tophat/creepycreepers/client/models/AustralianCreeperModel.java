package cat.tophat.creepycreepers.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.monster.CreeperEntity;

/**
 * A simplified version of the original model. Uses an
 * extension of the ghostly creeper model to save rewriting
 * common code.
 * <br><br>
 * Developer note: The render type is currently put as no cull
 * entity cutout. If planning to use a transparent texture, this
 * should use the other constructor within {@link GhostlyCreeperModel}.
 * 
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 */
public class AustralianCreeperModel<T extends CreeperEntity> extends GhostlyCreeperModel<T> {

	/**
	 * Constructor for the model.
	 * 
	 * @param modelSize The model inflation of the creeper. Do not hardcode.
	 */
	public AustralianCreeperModel(float modelSize) {
		super(RenderType::getEntityCutoutNoCull, modelSize);
		this.body.rotateAngleZ = (float) Math.PI;
	}
}
