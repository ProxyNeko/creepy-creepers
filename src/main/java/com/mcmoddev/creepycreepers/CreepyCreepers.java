package com.mcmoddev.creepycreepers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mcmoddev.creepycreepers.common.init.Config;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreepyCreepers.MOD_ID)
public class CreepyCreepers {

	/**
	 * Set the mods ID.
	 */
	public static final String MOD_ID = "creepy-creepers";
	/**
	 * Setup the logger for the mod.
	 */
	private static final Logger LOGGER = LogManager.getLogger("Creepy Creepers");

	/**
	 * Register things.
	 */
	public CreepyCreepers() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::fingerprintViolation);
		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPECIFICATION);
	}

	/**
	 * Check if the mod is signed and warn if it is not.
	 *
	 * @param event the event fired.
	 */
	private void fingerprintViolation(FMLFingerprintViolationEvent event) {
		LOGGER.error("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been "
			+ "tampered with. This version will NOT be supported! Please download the mod from CurseForge for a "
			+ "supported and signed version of the mod.");
	}
}
