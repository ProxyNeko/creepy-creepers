package cat.tophat.creepycreepers.common.entities;

import net.minecraft.entity.monster.CreeperEntity;

/**
 * A simple interface used to allow greater
 * functionality to be added to {@link CreeperEntity}.
 */
public interface ICreeper {

	/**
	 * Called when the creeper has been first ignited.
	 */
	void onIgnited();
}
