package com.mcmoddev.creepycreepers.client.models;

import com.google.common.collect.ImmutableList;
import com.mcmoddev.creepycreepers.common.entities.AustralianCreeperEntity;

import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class AustralianCreeperModel extends SegmentedModel<AustralianCreeperEntity> {

	public ModelRendererOffset creeper_body; //Body
	public ModelRenderer creeper_head; //Head
	public ModelRenderer creeper_field; //Head Field

	public AustralianCreeperModel() {
		textureWidth = 64;
		textureHeight = 32;

		creeper_field = new ModelRenderer(this, 32, 0);
		creeper_field.setRotationPoint(0.0F, 6.0F, 0.0F);
		creeper_field.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		setRotateAngle(creeper_field, 0.0F, 0.0F, 3.141592653589793F);

		creeper_head = new ModelRenderer(this, 0, 0);
		creeper_head.setRotationPoint(0.0F, 6.0F, 0.0F);
		creeper_head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		setRotateAngle(creeper_head, 0.0F, 0.0F, 3.141592653589793F);

		creeper_body = new ModelRendererOffset(this, 16, 16);
		creeper_body.setRotationPoint(0.0F, 6.0F, 0.0F);
		creeper_body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		setRotateAngle(creeper_body, 0.0F, 0.0F, -3.141592653589793F);

		creeper_body.addChild(creeper_field);
		creeper_body.addChild(creeper_head);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(creeper_body);
	}

	@Override
	public void setRotationAngles(AustralianCreeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		creeper_head.rotateAngleX = headPitch * 0.0047F;
		creeper_head.rotateAngleY = netHeadYaw * 0.0047F;
		creeper_body.offsetY = MathHelper.cos(ageInTicks * 0.15F) * 0.15F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
