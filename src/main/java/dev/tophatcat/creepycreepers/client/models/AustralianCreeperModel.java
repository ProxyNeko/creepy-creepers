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

import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.monster.CreeperEntity;

/**
 * A simplified version of the original model. Uses an
 * extension of the ghostly creeper model to save rewriting
 * common code.
 * <br><br>
 * Developer note: The render type is currently put as no cull
 * entity cutout. If planning to use a transparent texture, this
 * should use the other constructor within {@link GhostlyCreeperModel}.
 *
 * @param <T> A class that extends {@link CreeperEntity}. Should be left generic.
 */
public class AustralianCreeperModel<T extends CreeperEntity> extends GhostlyCreeperModel<T> {

	/**
	 * Constructor for the model.
	 *
	 * @param modelSize The model inflation of the creeper. Do not hardcode.
	 */
	public AustralianCreeperModel(float modelSize) {
		super(RenderType::entityCutoutNoCull, modelSize);
		this.body.zRot = (float) Math.PI;
	}
}
