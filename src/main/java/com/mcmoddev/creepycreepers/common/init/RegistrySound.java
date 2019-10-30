package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrySound {

    public static final SoundEvent CREEPERSCREAM_SOUND = getSound("creeperscream");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(CREEPERSCREAM_SOUND);
    }

    private static SoundEvent getSound(ResourceLocation location) {
        return new SoundEvent(location).setRegistryName(location);
    }

    private static SoundEvent getSound(String location) {
        return getSound(new ResourceLocation(CreepyCreepers.MODID, location));
    }
}
