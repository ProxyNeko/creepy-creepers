package cat.tophat.creepycreepers.common.entities;

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
	private final Lazy<SoundEvent> ignitedSound;
	
	/**
	 * Constructor for the creeper.
	 * 
	 * @param type The entity type.
	 * @param worldIn The current world.
	 * @param ingitedSound A supplier of the ignition sound.
	 */
	public CreepyCreeperEntity(EntityType<? extends CreepyCreeperEntity> type, World worldIn, Supplier<SoundEvent> ingitedSound) {
		super(type, worldIn);
		this.ignitedSound = Lazy.of(ingitedSound);
	}

	/**
	 * Plays the step sound when the mob walks over a certain block.
	 * 
	 * @param pos   The position to play the sound at.
	 * @param block The block under the mob.
	 */
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {}

	/**
	 * Called when the creeper has been first ignited.
	 * <br><br>
	 * This method is extending an implementation on a
	 * mixin so no override is necessary.
	 */
	public void onIgnited() {
		this.playSound(this.ignitedSound.get(), 1.0F, 1.0F);
	}
}
