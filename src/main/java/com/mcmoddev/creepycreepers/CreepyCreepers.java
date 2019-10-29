package com.mcmoddev.creepycreepers;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CreepyCreepers.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class CreepyCreepers {

    /**
     * Set the mods ID.
     */
    public static final String MODID = "creepy-creepers";
    /**
     * Setup the logger for the mod.
     */
    private static final Logger LOGGER = LogManager.getLogger("Creepy Creepers");

    /**
     * Register things.
     */
    public CreepyCreepers() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::fingerprintViolation);
    }

    /**
     * Check if the mod is signed and warn if it is not.
     *
     * @param event
     */
    private void fingerprintViolation(FMLFingerprintViolationEvent event) {
        LOGGER.warn("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been " +
                "tampered with. This version will NOT be supported! Please download the mod from CurseForge for a " +
                "supported and signed version of the mod.");
    }

    /**
     * Setup a creative tab for the mod.
     */
    public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.CREEPER_HEAD);
        }
    };
}
