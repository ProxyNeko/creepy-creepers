package cat.tophat.creepycreepers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cat.tophat.creepycreepers.common.entities.ICreeper;
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
	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "playSound(Lnet/minecraft/util/SoundEvent;FF)V"))
	public void playIgnitionSound(CreeperEntity entity, SoundEvent sound, float volume, float pitch) {
		this.onIgnited(volume, pitch);
	}
	
	@Override
	public void onIgnited(float volume, float pitch) {
		this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, volume, pitch);
	}
}
