package dev.nertzhul.creepycreepers.client.rendering.snowycreeper;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperModel;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class SnowyCreeperModel extends CcCreeperModel<SnowyCreeper> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(CreepyCreepers.resource("snowy_creeper"), "main");
    
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    
    public SnowyCreeperModel(ModelPart pRoot) {
        super(pRoot);
        this.head = pRoot.getChild("head");
        this.leftHindLeg = pRoot.getChild("right_hind_leg");
        this.rightHindLeg = pRoot.getChild("left_hind_leg");
        this.leftFrontLeg = pRoot.getChild("right_front_leg");
        this.rightFrontLeg = pRoot.getChild("left_front_leg");
    }
    
    @Override
    public void setupAnim(SnowyCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
