/*
 * Creepy Creepers - https://github.com/tophatcats-mods/creepy-creepers
 * Copyright (C) 2016-2022 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * Specifically version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package dev.tophatcat.creepycreepers.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.tophatcat.creepycreepers.CreepyCreepers;
import dev.tophatcat.creepycreepers.entities.AustralianCreeperEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class AustralianCreeperModel<T extends AustralianCreeperEntity> extends EntityModel<T> {

    public static final ModelLayerLocation AUSTRALIAN_CREEPER_LAYER_LOCATION = new ModelLayerLocation(
        new ResourceLocation(CreepyCreepers.MOD_ID, "australian_creeper"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart backRightLeg;
    private final ModelPart frontLeftLeg;
    private final ModelPart frontRightLeg;
    private final ModelPart backLeftLeg;

    public AustralianCreeperModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.backRightLeg = root.getChild("backRightLeg");
        this.frontLeftLeg = root.getChild("frontLeftLeg");
        this.frontRightLeg = root.getChild("frontRightLeg");
        this.backLeftLeg = root.getChild("backLeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition body = partDefinition.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(16, 16)
                .addBox(-4.0F, -6.0F, -2.0F,
                    8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition head = partDefinition.addOrReplaceChild("head",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, 0.0F, -4.0F,
                    8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition backRightLeg = partDefinition.addOrReplaceChild("backRightLeg",
            CubeListBuilder.create(), PartPose.offset(-2.0F, 4.0F, 4.0F));

        PartDefinition backRightLegRotation = backRightLeg.addOrReplaceChild("backRightLegRotation",
            CubeListBuilder.create().texOffs(0, 16)
                .addBox(0.0F, -6.0F, 2.0F, 4.0F,
                    6.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(2.0F, -6.0F, -4.0F,
                0.0F, 0.0F, -3.1416F));

        PartDefinition frontRightLeg = partDefinition.addOrReplaceChild("frontRightLeg",
            CubeListBuilder.create(), PartPose.offset(-2.0F, 4.0F, -4.0F));

        PartDefinition frontRightLegRotation = frontRightLeg.addOrReplaceChild("frontRightLegRotation",
            CubeListBuilder.create().texOffs(0, 16)
                .addBox(0.0F, -6.0F, -6.0F,
                    4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(2.0F, -6.0F, 4.0F,
                0.0F, 0.0F, -3.1416F));

        PartDefinition frontLeftLeg = partDefinition.addOrReplaceChild("frontLeftLeg",
            CubeListBuilder.create(), PartPose.offset(2.0F, 4.0F, -4.0F));

        PartDefinition frontLeftLegRotation = frontLeftLeg.addOrReplaceChild("frontLeftLegRotation",
            CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, -3.0F, -2.0F,
                    4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F,
                0.0F, 0.0F, -3.1416F));

        PartDefinition backLeftLeg = partDefinition.addOrReplaceChild("backLeftLeg",
            CubeListBuilder.create(), PartPose.offset(2.0F, 4.0F, 4.0F));

        PartDefinition backLeftLegRotation = backLeftLeg.addOrReplaceChild("backLeftLegRotation",
            CubeListBuilder.create().texOffs(0, 16)
                .addBox(-4.0F, -6.0F, 2.0F,
                    4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-2.0F, -6.0F, -4.0F,
                0.0F, 0.0F, -3.1416F));


        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        head.xRot = headPitch * ((float) Math.PI / 180F);
        backRightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        backLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        frontRightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        frontLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        backRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        backLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
