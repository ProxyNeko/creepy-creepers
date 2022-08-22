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

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tophatcat.creepycreepers.client.models.AustralianCreeperModel;
import dev.tophatcat.creepycreepers.entities.AustralianCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class AustralianCreeperRenderer extends MobRenderer<AustralianCreeperEntity, AustralianCreeperModel<AustralianCreeperEntity>> {

    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("minecraft",
        "textures/entity/creeper/creeper.png");

    public AustralianCreeperRenderer(final EntityRendererProvider.Context context) {
        super(context, new AustralianCreeperModel<>(
            context.bakeLayer(AustralianCreeperModel.AUSTRALIAN_CREEPER_LAYER_LOCATION)), 0.0F);
    }

    @Override
    protected void scale(@Nonnull AustralianCreeperEntity entity, @Nonnull PoseStack poseStack, float partialTickTime) {
        float f = entity.getSwelling(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(AustralianCreeperEntity livingEntity, float partialTicks) {
        float f = livingEntity.getSwelling(partialTicks);
        return f * 10.0F % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull AustralianCreeperEntity entity) {
        return RESOURCE_LOCATION;
    }
}
