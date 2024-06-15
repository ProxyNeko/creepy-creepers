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
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class GhostlyCreeperModel extends CcCreeperModel<GhostlyCreeper> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(CreepyCreepers.resource("ghostly_creeper"), "main");
    public static final ModelLayerLocation ARMOR_LAYER = new ModelLayerLocation(CreepyCreepers.resource("ghostly_creeper"), "armor");
    
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    
    public GhostlyCreeperModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }
    
    public static LayerDefinition createBodyLayer(CubeDeformation pDeformation) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        
        partDefinition.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, pDeformation),
            PartPose.offset(0.0F, 6.0F, 0.0F)
        );
        
        partDefinition.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, pDeformation),
            PartPose.offset(0.0F, 6.0F, 0.0F)
        );
        
        partDefinition.addOrReplaceChild("tail",
            CubeListBuilder.create()
                .texOffs(40, 0).addBox(-4.0F, 3.0F, -2.0F, 8.0F, 12.0F, 4.0F, pDeformation.extend(-0.3F))
                .texOffs(40, 16).addBox(-4.0F, 6.0F, -2.0F, 8.0F, 12.0F, 4.0F, pDeformation.extend(-0.6F)),
            PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.2F, 0.0F, 0.0F)
        );
        
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
    
    @Override
    public void setupAnim(GhostlyCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        
        if (!entity.isIgnited()) {
            this.head.y = Mth.cos(ageInTicks * 0.07F) * 0.6F;
            this.body.y = Mth.cos(ageInTicks * 0.07F) * 0.6F;
            this.tail.y = Mth.cos(ageInTicks * 0.07F) * 0.6F;
            
            float j = Math.min(limbSwingAmount / 0.2F, 1.0F);
            this.body.xRot = j * 0.4F;
            this.tail.xRot = j * 0.4F;
        }
    }
    
    @Override
    public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, int $$4) {
        this.head.render($$0, $$1, $$2, $$3, FastColor.ARGB32.color(191, $$4));
        this.body.render($$0, $$1, $$2, $$3, FastColor.ARGB32.color(191, $$4));
        this.tail.render($$0, $$1, $$2, $$3, FastColor.ARGB32.color(191, $$4));
    }
}
