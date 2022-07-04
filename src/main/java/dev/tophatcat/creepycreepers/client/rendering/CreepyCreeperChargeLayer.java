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

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * A more compatible version of {@link CreeperChargeLayer}.
 * Modded creepers should never have a reason to explicitly
 * reference this class. It is already attached to {@link CreepyCreeperRenderer}.
 *
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 * @param <M> A class that extends {@link EntityModel}. Should be left generic.
 */
public class CreepyCreeperChargeLayer<T extends CreeperEntity, M extends EntityModel<T>> extends EnergyLayer<T, M> {

    private static final ResourceLocation LIGHTNING_TEXTURE
        = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final M model;
    private final ResourceLocation texture;

    /**
     * Constructor for the layer. Uses the default texture.
     *
     * @param renderer The renderer currently adding this layer.
     * @param model    The model to render the texture over.
     */
    public CreepyCreeperChargeLayer(IEntityRenderer<T, M> renderer, M model) {
        this(renderer, model, LIGHTNING_TEXTURE);
    }

    /**
     * Constructor for the layer.
     *
     * @param renderer The renderer currently adding this layer.
     * @param model    The model to render the texture over.
     * @param texture  The texture to render over the model.
     */
    public CreepyCreeperChargeLayer(IEntityRenderer<T, M> renderer, M model, ResourceLocation texture) {
        super(renderer);
        this.model = model;
        this.texture = texture;
    }

    @Override
    protected float xOffset(float partialTicks) {
        return partialTicks * 0.01f;
    }

    @Nonnull
    @Override
    protected ResourceLocation getTextureLocation() {
        return this.texture;
    }

    @Nonnull
    @Override
    protected EntityModel<T> model() {
        return this.model;
    }

}
