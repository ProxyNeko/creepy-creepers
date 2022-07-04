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

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * An fully modifiable version of {@link CreeperModel}.
 * Modded creepers should extend this class or call
 * directly if planning to modify the creeper model
 * in any way.
 *
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 */
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
     * @param renderType    The render layer the creeper should be in.
     * @param modelSize     The model inflation of the creeper. Do not hardcode.
     * @param textureWidth  The width of the creeper texture.
     * @param textureHeight The height of the creeper texture.
     */
    public AbstractCreepyCreeperModel(Function<ResourceLocation, RenderType> renderType, float modelSize,
                                      int textureWidth, int textureHeight) {
        super(renderType);
        this.texWidth = textureWidth;
        this.texHeight = textureHeight;
        this.head = new ModelRendererOffset(this, 0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F,
            8.0F, modelSize);
        this.head.setPos(0.0F, 6.0F, 0.0F);
        this.body = new ModelRendererOffset(this, 16, 16);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F,
            4.0F, modelSize);
        this.body.setPos(0.0F, 6.0F, 0.0F);
        this.legBackRight = new ModelRendererOffset(this, 0, 16);
        this.legBackRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F,
            6.0F, 4.0F, modelSize);
        this.legBackRight.setPos(-2.0F, 18.0F, 4.0F);
        this.legBackLeft = new ModelRendererOffset(this, 0, 16);
        this.legBackLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F,
            6.0F, 4.0F, modelSize);
        this.legBackLeft.setPos(2.0F, 18.0F, 4.0F);
        this.legFrontRight = new ModelRendererOffset(this, 0, 16);
        this.legFrontRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F,
            6.0F, 4.0F, modelSize);
        this.legFrontRight.setPos(-2.0F, 18.0F, -4.0F);
        this.legFrontLeft = new ModelRendererOffset(this, 0, 16);
        this.legFrontLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F,
            6.0F, 4.0F, modelSize);
        this.legFrontLeft.setPos(2.0F, 18.0F, -4.0F);
    }

    @Nonnull
    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.head, this.body, this.legBackRight,
            this.legBackLeft, this.legFrontRight, this.legFrontLeft);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.legBackRight.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F
            + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = MathHelper.cos(limbSwing * 0.6662F
            + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
