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
package dev.tophatcat.creepycreepers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.tophatcat.creepycreepers.common.entities.ICreeper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

/**
 * A mixin of the underlying {@link CreeperEntity}
 * class. Used to allow custom logic to occur on
 * mob ignition.
 */
@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends MonsterEntity implements ICreeper {

	private CreeperEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	/**
	 * Used to play the ignition sound within the creeper tick method.
	 */
	@Redirect(method = "tick()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/entity/monster/CreeperEntity;playSound(Lnet/minecraft/util/SoundEvent;FF)V"))
	public void playIgnitionSound(CreeperEntity entity, SoundEvent sound, float volume, float pitch) {
		this.onIgnited(volume, pitch);
	}

	@Override
	public void onIgnited(float volume, float pitch) {
		this.playSound(SoundEvents.CREEPER_PRIMED, volume, pitch);
	}
}
