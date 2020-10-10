package cat.tophat.creepycreepers.common.init;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class MobSpawner extends Spawners {

	private static final Field ITEM_WEIGHT = ObfuscationReflectionHelper.findField(WeightedRandom.Item.class, "field_76292_a");
	private final int weight;
	
	public MobSpawner(EntityType<?> type, int weight, int minCount, int maxCount) {
		super(type, weight, minCount, maxCount);
		this.weight = weight;
	}

	public void invalidate() {
		try {
			ITEM_WEIGHT.set(this, 0);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void validate() {
		try {
			ITEM_WEIGHT.set(this, weight);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
