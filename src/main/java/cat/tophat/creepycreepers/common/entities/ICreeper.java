package cat.tophat.creepycreepers.common.entities;

import net.minecraft.entity.monster.CreeperEntity;

/**
 * A simple interface used to allow greater
 * functionality to be added to {@link CreeperEntity}.
 */
public interface ICreeper {

	/**
	 * Called when the creeper has been first ignited.
	 * 
	 * @param volume The original sound volume.
	 * @param pitch The original sound pitch.
	 */
	void onIgnited(float volume, float pitch);
}
