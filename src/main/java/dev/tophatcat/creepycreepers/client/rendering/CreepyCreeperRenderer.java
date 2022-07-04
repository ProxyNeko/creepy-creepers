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
package dev.tophatcat.creepycreepers.client.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * A fully modifiable version of {@link CreeperRenderer}.
 * Modded creepers should extend this class regardless.
 *
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 * @param <M> A class that extends {@link EntityModel}. Should be left generic.
 */
public class CreepyCreeperRenderer<T extends CreeperEntity, M extends EntityModel<T>> extends MobRenderer<T, M> {

    private final ResourceLocation texture;

    /**
     * Constructor for the renderer. Applies the charge layer using the default texture.
     *
     * @param renderManager The manager for entity renders.
     * @param modelCreator  An abstract model creator to grab the default and charged creeper models if applicable.
     * @param shadowSize    The shadow size of the creeper.
     * @param texture       The texture location of the creeper.
     */
    public CreepyCreeperRenderer(EntityRendererManager renderManager, Function<Float, M> modelCreator,
                                 float shadowSize, ResourceLocation texture) {
        this(renderManager, modelCreator, shadowSize, texture, true);
    }

    /**
     * Constructor for the renderer.
     *
     * @param renderManager    The manager for entity renders.
     * @param modelCreator     An abstract model creator to grab the default and charged creeper models if applicable.
     * @param shadowSize       The shadow size of the creeper.
     * @param texture          The texture location of the creeper.
     * @param applyChargeLayer If the charge layer should be attached to the renderer. Uses the default texture.
     */
    public CreepyCreeperRenderer(EntityRendererManager renderManager, Function<Float, M> modelCreator,
                                 float shadowSize, ResourceLocation texture, boolean applyChargeLayer) {
        this(renderManager, modelCreator, shadowSize, texture, null, applyChargeLayer);
    }

    /**
     * Constructor for the renderer.
     *
     * @param renderManager      The manager for entity renders.
     * @param modelCreator       An abstract model creator to grab the default and charged creeper models if applicable.
     * @param shadowSize         The shadow size of the creeper.
     * @param texture            The texture location of the creeper.
     * @param chargeLayerTexture If not null, uses the texture for the charge layer. Default to vanilla charge texture.
     * @param applyChargeLayer   If the charge layer should be attached to the renderer.
     */
    public CreepyCreeperRenderer(EntityRendererManager renderManager, Function<Float, M> modelCreator,
                                 float shadowSize, ResourceLocation texture,
                                 @Nullable ResourceLocation chargeLayerTexture, boolean applyChargeLayer) {
        super(renderManager, modelCreator.apply(0.0f), shadowSize);
        this.texture = texture;
        if (applyChargeLayer) {
            if (chargeLayerTexture != null)
                this.addLayer(new CreepyCreeperChargeLayer<>(this,
                    modelCreator.apply(2.0f), chargeLayerTexture));
            else this.addLayer(new CreepyCreeperChargeLayer<>(this, modelCreator.apply(2.0f)));
        }
    }

    @Override
    protected void scale(CreeperEntity entityLivingBase, MatrixStack matrixStack, float partialTickTime) {
        float f = entityLivingBase.getSwelling(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStack.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(CreeperEntity livingEntity, float partialTicks) {
        float f = livingEntity.getSwelling(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return this.texture;
    }
}
