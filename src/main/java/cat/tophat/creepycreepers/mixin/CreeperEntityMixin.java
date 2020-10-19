package cat.tophat.creepycreepers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import cat.tophat.creepycreepers.common.entities.ICreeper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

/**
 * A mixin of the underlying {@link CreeperEntity}
 * class. Used to allow custom logic to occur on
 * mob ignition.
 */
@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends MonsterEntity implements ICreeper {

	@Shadow private int lastActiveTime;
	@Shadow private int timeSinceIgnited;
	@Shadow private int fuseTime;
	@Shadow public abstract boolean hasIgnited();
	@Shadow public abstract void setCreeperState(int state);
	@Shadow public abstract int getCreeperState();
	@Shadow protected abstract void explode();
	
	private CreeperEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	/**
	 * Creeper tick
	 */
	@Overwrite
	public void tick() {
		if (this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if (this.hasIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();
			if (i > 0 && this.timeSinceIgnited == 0) {
				this.onIgnited();
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}
		super.tick();
	}
	
	@Override
	public void onIgnited() {
		this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
	}
}
