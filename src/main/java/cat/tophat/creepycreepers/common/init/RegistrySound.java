package cat.tophat.creepycreepers.common.init;

import cat.tophat.creepycreepers.CreepyCreepers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrySound {

    public static final SoundEvent CREEPER_SCREAM_SOUND = getSound("creeperscream");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(CREEPER_SCREAM_SOUND);
    }

    private static SoundEvent getSound(ResourceLocation location) {
        return new SoundEvent(location).setRegistryName(location);
    }

    private static SoundEvent getSound(String location) {
        return getSound(new ResourceLocation(CreepyCreepers.MOD_ID, location));
    }
}
