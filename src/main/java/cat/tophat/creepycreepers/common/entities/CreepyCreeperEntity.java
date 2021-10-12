package cat.tophat.creepycreepers.common.entities;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;

/**
 * A more compatible version of {@link Creeper}.
 * Uses a mixin to allow custom ignited sounds to be
 * played.
 */
public class CreepyCreeperEntity extends Creeper {

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
	public CreepyCreeperEntity(EntityType<? extends CreepyCreeperEntity> type, Level worldIn, Supplier<SoundEvent> ignitedSound) {
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