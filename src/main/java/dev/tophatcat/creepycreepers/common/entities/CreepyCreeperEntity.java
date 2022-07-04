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
package dev.tophatcat.creepycreepers.common.entities;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

/**
 * A more compatible version of {@link CreeperEntity}.
 * Uses a mixin to allow custom ignited sounds to be
 * played.
 */
public class CreepyCreeperEntity extends CreeperEntity {

	/**
	 * The sound to be played when ignited.
	 */
	protected final Lazy<SoundEvent> ignitedSound;

	/**
	 * Constructor for the creeper.
	 *
	 * @param type The entity type.
	 * @param worldIn The current world.
	 * @param ignitedSound A supplier of the ignition sound.
	 */
	public CreepyCreeperEntity(EntityType<? extends CreepyCreeperEntity> type, World worldIn, Supplier<SoundEvent> ignitedSound) {
		super(type, worldIn);
		this.ignitedSound = Lazy.of(ignitedSound);
	}

	/**
	 * Plays the step sound when the mob walks over a certain block.
	 *
	 * @param pos              The position to play the sound at.
	 * @param blockUnderneath  The block under the mob.
	 */
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockUnderneath) {}

	/**
	 * Called when the creeper has been first ignited.
	 * <br><br>
	 * This method is extending an implementation on a
	 * mixin so no override is necessary.
	 */
	public void onIgnited(float volume, float pitch) {
		this.playSound(this.ignitedSound.get(), 1.0F, 1.0F);
	}
}
