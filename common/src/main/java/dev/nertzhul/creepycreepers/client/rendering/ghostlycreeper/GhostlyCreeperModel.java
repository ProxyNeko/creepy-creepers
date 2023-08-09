package dev.nertzhul.creepycreepers.client.rendering.ghostlycreeper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperModel;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
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

public class GhostlyCreeperModel extends CcCreeperModel<GhostlyCreeper> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(CreepyCreepers.resource("ghostly_creeper"), "main");
    
    private final ModelPart head;
    private final ModelPart body;
    
    public GhostlyCreeperModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
    }
    
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        
        partDefinition.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(16, 16)
                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 6.0F, 0.0F));
        
        partDefinition.addOrReplaceChild("head",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.0F));
        
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
    
    @Override
    public void setupAnim(GhostlyCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        
        if (!entity.isIgnited()) {
            this.head.y = Mth.cos(ageInTicks * 0.07F) * 0.6F;
            this.body.y = Mth.cos(ageInTicks * 0.07F) * 0.6F;
        }
    }
    
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, 0.65F);
        this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, 0.65F);
    }
}
