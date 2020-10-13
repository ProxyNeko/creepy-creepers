package cat.tophat.creepycreepers.client.models;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.monster.CreeperEntity;

/**
 * A simplified version of the original model. Uses an
 * extension of the ghostly creeper model to save rewriting
 * common code.
 * <br><br>
 * This code is still a work in progress. A texture is missing
 * and the original intention of this entity seems to be a bit
 * abstract.
 * 
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 */
public class AustralianCreeperModel<T extends CreeperEntity> extends GhostlyCreeperModel<T> {

	public AustralianCreeperModel(float modelSize) {
		super(RenderType::getEntityCutoutNoCull, modelSize);
		//this.head.setRotationPoint(0.0F, -8.0F, 0.0F); Not sure what to do here, get information from TopHatCat
		//this.head.rotateAngleZ = (float) Math.PI;
		this.body.rotateAngleZ = (float) Math.PI;
	}
}
