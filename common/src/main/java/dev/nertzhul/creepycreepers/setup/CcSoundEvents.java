package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.util.registry.RegistryObject;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class CcSoundEvents {
    public static final RegistryProvider<SoundEvent> SOUNDS = RegistryProvider.create(Registries.SOUND_EVENT, CreepyCreepers.MOD_ID);
    
    public static final RegistryObject<SoundEvent> GHOSTLY_CREEPER_SCREAM = register("ghostly_creeper_scream");
    
    private static RegistryObject<SoundEvent> register(String pName) {
        return SOUNDS.register(pName, () -> SoundEvent.createVariableRangeEvent(CreepyCreepers.resource(pName)));
    }
    
    public static void register() {
        // no-op
    }
}
