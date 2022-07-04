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
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * A simplified version of the original model. Also specifies
 * the correct render layer so that it can properly be translucent.
 *
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 */
public class GhostlyCreeperModel<T extends CreeperEntity> extends AbstractCreepyCreeperModel<T> {

    /**
     * Constructor for the model.
     *
     * @param modelSize The model inflation of the creeper. Do not hardcode.
     */
    public GhostlyCreeperModel(float modelSize) {
        this(RenderType::entityTranslucent, modelSize);
    }

    /**
     * Constructor for the model.
     *
     * @param renderType The render layer the creeper should be in. Used specifically for allowing the australian creeper to extend.
     * @param modelSize  The model inflation of the creeper. Do not hardcode.
     */
    public GhostlyCreeperModel(Function<ResourceLocation, RenderType> renderType, float modelSize) {
        super(renderType, modelSize, 64, 32);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.body.addChild(this.head);
    }

    @Nonnull
    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.0047F;
        this.head.yRot = netHeadYaw * 0.0047F;
        if (!entity.isIgnited()) {
            this.body.offsetY = MathHelper.cos(ageInTicks * 0.15F) * 0.15F;
        }
    }
}
