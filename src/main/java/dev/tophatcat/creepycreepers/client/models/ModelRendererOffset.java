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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * An extension of the {@link ModelRenderer} class
 * to allow for offsets in translation to occur without
 * affecting the original position.
 */
public class ModelRendererOffset extends ModelRenderer {

    public float offsetX, offsetY, offsetZ;

    public ModelRendererOffset(Model model) {
        super(model);
    }

    public ModelRendererOffset(Model model, int texOffX, int texOffY) {
        super(model, texOffX, texOffY);
    }

    public ModelRendererOffset(int textureWidthIn, int textureHeightIn, int textureOffsetXIn, int textureOffsetYIn) {
        super(textureWidthIn, textureHeightIn, textureOffsetXIn, textureOffsetYIn);
    }

    @Override
    public void translateAndRotate(MatrixStack matrixStackIn) {
        matrixStackIn.translate(this.offsetX, this.offsetY, this.offsetZ);
        super.translateAndRotate(matrixStackIn);
    }
}
