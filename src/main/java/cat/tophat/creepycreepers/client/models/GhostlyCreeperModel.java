package cat.tophat.creepycreepers.client.models;

import com.google.common.collect.ImmutableList;

import cat.tophat.creepycreepers.common.entities.GhostlyCreeperEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class GhostlyCreeperModel extends SegmentedModel<GhostlyCreeperEntity> {

	private ModelRenderer creeper_head;
	private ModelRenderer head_field;
	private ModelRendererOffset creeper_body;

	public GhostlyCreeperModel() {
		textureWidth = 64;
		textureHeight = 32;

		head_field = new ModelRenderer(this, 32, 0);
		head_field.setRotationPoint(0.0F, 6.0F, 0.0F);
		head_field.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);

		creeper_head = new ModelRenderer(this, 0, 0);
		creeper_head.setRotationPoint(0.0F, 0F, 0.0F);
		creeper_head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);

		creeper_body = new ModelRendererOffset(this, 16, 16);
		creeper_body.setRotationPoint(0.0F, 6.0F, 0.0F);
		creeper_body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);

		creeper_body.addChild(head_field);
		creeper_body.addChild(creeper_head);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(creeper_body);
	}

	@Override
	public void setRotationAngles(GhostlyCreeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		creeper_head.rotateAngleX = headPitch * 0.0047F;
		creeper_head.rotateAngleY = netHeadYaw * 0.0047F;
		if (!entity.hasIgnited()) {
			creeper_body.offsetY = MathHelper.cos(ageInTicks * 0.15F) * 0.15F;
		}
	}
}
