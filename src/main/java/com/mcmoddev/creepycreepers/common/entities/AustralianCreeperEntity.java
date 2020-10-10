package com.mcmoddev.creepycreepers.common.entities;

import com.mcmoddev.creepycreepers.common.init.RegistrySound;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Setup the australian creeper entity.
 */
public class AustralianCreeperEntity extends CreeperEntity {

	/**
	 * The time since the creepers last ignition.
	 */
	private int timeSinceIgnited;

	/**
	 * @param type  The entity type.
	 * @param world The world.
	 */
	public AustralianCreeperEntity(EntityType<? extends CreeperEntity> type, World world) {
		super(type, world);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		if (isAlive()) {
			if (hasIgnited()) {
				setCreeperState(1);
			}

			int i = getCreeperState();
			if (i > 0 && timeSinceIgnited == 0) {
				playSound(RegistrySound.CREEPER_SCREAM_SOUND, 1.0F, 1.0F);
			}

			timeSinceIgnited += i;
			if (timeSinceIgnited < 0) {
				timeSinceIgnited = 0;
			}

			int fuseTime = 30;
			if (timeSinceIgnited >= fuseTime) {
				timeSinceIgnited = fuseTime;
				explode();
			}
		}
		super.tick();
	}

	/**
	 * Creates an explosion as determined by this creeper's power and explosion radius.
	 */
	private void explode() {
		if (!world.isRemote) {
			Explosion.Mode explosionMode = ForgeEventFactory.getMobGriefingEvent(world, this)
				? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
			dead = true;
			int explosionRadius = 3;
			world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) explosionRadius, explosionMode);
			remove();
			spawnLingeringCloud();
		}

	}

	/**
	 * Spawn the after explosion cloud effect for when the creeper blows up.
	 */
	private void spawnLingeringCloud() {
		Collection<EffectInstance> collection = getActivePotionEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(world, this.getPosX(), this.getPosY(), this.getPosZ());
			areaeffectcloudentity.setRadius(2.5F);
			areaeffectcloudentity.setRadiusOnUse(-0.5F);
			areaeffectcloudentity.setWaitTime(10);
			areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
			areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius()
				/ (float) areaeffectcloudentity.getDuration());

			for (EffectInstance effectinstance : collection) {
				areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
			}

			world.addEntity(areaeffectcloudentity);
		}

	}

	/**
	 * @param pos   The position to play the sound at.
	 * @param block The block under the mob.
	 */
	@Override
	protected void playStepSound(@Nonnull BlockPos pos, BlockState block) {}
}
