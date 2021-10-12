package cat.tophat.creepycreepers.client.models;

import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * A simplified version of the original model. Also specifies
 * the correct render layer so it can properly be translucent.
 * 
 * @param <T> A class that extends {@link Creeper}. Should be left generic.
 */
public class GhostlyCreeperModel<T extends Creeper> extends AbstractCreepyCreeperModel<T> {

	/**
	 * Constructor for the model.
	 * 
	 * @param modelSize The model inflation of the creeper. Do not hardcode.
	 */
	public GhostlyCreeperModel(float modelSize) {
		this(RenderType::entityTranslucent, modelSize);
	}
	
	/**
	 * Constructor for the model.
	 * 
	 * @param renderType The render layer the creeper should be in. Used specifically for allowing the australian creeper to extend.
	 * @param modelSize The model inflation of the creeper. Do not hardcode.
	 */
	public GhostlyCreeperModel(Function<ResourceLocation, RenderType> renderType, float modelSize) {
		super(renderType, modelSize, 64, 32);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.body.addChild(this.head);
	}
	
	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(this.body);
	}
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * 0.0047F;
		this.head.yRot = netHeadYaw * 0.0047F;
		if (!entity.isIgnited()) {
			this.body.offsetY = Mth.cos(ageInTicks * 0.15F) * 0.15F;
		}
	}
}