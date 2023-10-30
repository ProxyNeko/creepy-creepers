package dev.nertzhul.creepycreepers.client.rendering.corruptedcreeper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperModel;
import dev.nertzhul.creepycreepers.entities.CorruptedCreeper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class CorruptedCreeperModel extends CcCreeperModel<CorruptedCreeper> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(CreepyCreepers.resource("corrupted_creeper"), "main");
    public static final ModelLayerLocation ARMOR_LAYER = new ModelLayerLocation(CreepyCreepers.resource("corrupted_creeper"), "armor");
    
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    
    protected CorruptedCreeperModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }
    
    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
            PartPose.offset(0.0F, 6.0F, 0.0F));
        head.addOrReplaceChild("inner_head", CubeListBuilder.create()
            .texOffs(32, 0).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation.extend(-0.1F)),
            PartPose.offset(0.0F, 18.0F, 0.0F));
        
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
            .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
            PartPose.offset(0.0F, 6.0F, 0.0F));
        body.addOrReplaceChild("inner_body", CubeListBuilder.create()
            .texOffs(40, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation.extend(-0.1F)),
            PartPose.offset(0.0F, 0.0F, 0.0F));
        
        partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create()
            .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation),
            PartPose.offset(-2.0F, 18.0F, 4.0F));
        
        partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create()
            .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation),
            PartPose.offset(2.0F, 18.0F, 4.0F));
        
        partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create()
            .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation),
            PartPose.offset(-2.0F, 18.0F, -4.0F));
        
        partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create()
            .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation),
            PartPose.offset(2.0F, 18.0F, -4.0F));
        
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
    
    @Override
    public void setupAnim(CorruptedCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
        this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
    
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
