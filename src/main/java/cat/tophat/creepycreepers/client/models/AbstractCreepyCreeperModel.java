package cat.tophat.creepycreepers.client.models;

import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * An fully modifiable version of {@link CreeperModel}.
 * Modded creepers should extend this class or call
 * directly if planning to modify the creeper model
 * in any way.
 * 
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 * */
public class AbstractCreepyCreeperModel<T extends CreeperEntity> extends SegmentedModel<T> {

	protected ModelRendererOffset head;
	protected ModelRendererOffset body;
	protected ModelRendererOffset legBackRight;
	protected ModelRendererOffset legBackLeft;
	protected ModelRendererOffset legFrontRight;
	protected ModelRendererOffset legFrontLeft;

	/**
	 * Constructor for the abstract model.
	 * 
	 * @param renderType The render layer the creeper should be in.
	 * @param modelSize The model inflation of the creeper. Do not hardcode.
	 * @param textureWidth The width of the creeper texture.
	 * @param textureHeight The height of the creeper texture.
	 */
	public AbstractCreepyCreeperModel(Function<ResourceLocation, RenderType> renderType, float modelSize, int textureWidth, int textureHeight) {
		super(renderType);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.head = new ModelRendererOffset(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
		this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.body = new ModelRendererOffset(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize);
		this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.legBackRight = new ModelRendererOffset(this, 0, 16);
		this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.legBackRight.setRotationPoint(-2.0F, 18.0F, 4.0F);
		this.legBackLeft = new ModelRendererOffset(this, 0, 16);
		this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.legBackLeft.setRotationPoint(2.0F, 18.0F, 4.0F);
		this.legFrontRight = new ModelRendererOffset(this, 0, 16);
		this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.legFrontRight.setRotationPoint(-2.0F, 18.0F, -4.0F);
		this.legFrontLeft = new ModelRendererOffset(this, 0, 16);
		this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, modelSize);
		this.legFrontLeft.setRotationPoint(2.0F, 18.0F, -4.0F);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.head, this.body, this.legBackRight, this.legBackLeft, this.legFrontRight, this.legFrontLeft);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}
