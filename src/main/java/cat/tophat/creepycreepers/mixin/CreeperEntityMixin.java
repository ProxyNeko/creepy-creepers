package cat.tophat.creepycreepers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cat.tophat.creepycreepers.common.entities.ICreeper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

/**
 * A mixin of the underlying {@link CreeperEntity}
 * class. Used to allow custom logic to occur on
 * mob ignition.
 */
@Mixin(Creeper.class)
public abstract class CreeperEntityMixin extends Monster implements ICreeper {
	
	private CreeperEntityMixin(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
	}

	/**
	 * Used to play the ignition sound within the creeper tick method.
	 */
	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "playSound(Lnet/minecraft/util/SoundEvent;FF)V"))
	public void playIgnitionSound(Creeper entity, SoundEvent sound, float volume, float pitch) {
		this.onIgnited(volume, pitch);
	}
	
	@Override
	public void onIgnited(float volume, float pitch) {
		this.playSound(SoundEvents.CREEPER_PRIMED, volume, pitch);
	}
}
