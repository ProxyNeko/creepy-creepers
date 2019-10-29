package com.mcmoddev.spookyjam.client.models;

import com.mcmoddev.spookyjam.common.entities.GhostlyCreeperEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class GhostlyCreeperModel extends EntityModel<GhostlyCreeperEntity> {

    private RendererModel creeper_head;
    private RendererModel head_field;
    private RendererModel creeper_body;

    public GhostlyCreeperModel() {
        textureWidth = 64;
        textureHeight = 32;

        head_field = new RendererModel(this, 32, 0);
        head_field.setRotationPoint(0.0F, 6.0F, 0.0F);
        head_field.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);

        creeper_head = new RendererModel(this, 0, 0);
        creeper_head.setRotationPoint(0.0F, 0F, 0.0F);
        creeper_head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);

        creeper_body = new RendererModel(this, 16, 16);
        creeper_body.setRotationPoint(0.0F, 6.0F, 0.0F);
        creeper_body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);

        creeper_body.addChild(head_field);
        creeper_body.addChild(creeper_head);
    }

    @Override
    public void render(GhostlyCreeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        creeper_body.render(scale);
    }

    @Override
    public void setRotationAngles(GhostlyCreeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        creeper_head.rotateAngleX = headPitch * 0.0047F;
        creeper_head.rotateAngleY = netHeadYaw * 0.0047F;
        creeper_body.offsetY = MathHelper.cos(ageInTicks * 0.15F) * 0.15F;
    }

    public void setRotateAngles(RendererModel rendererModel, float x, float y, float z) {
        rendererModel.rotateAngleX = x;
        rendererModel.rotateAngleY = y;
        rendererModel.rotateAngleZ = z;
    }
}
