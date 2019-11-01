package com.mcmoddev.creepycreepers.common.init;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import com.mcmoddev.creepycreepers.common.misc.ItemProxysStaffOfPower;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = CreepyCreepers.MODID)
@Mod.EventBusSubscriber(modid = CreepyCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryItem {

	public static final Item PROXYS_STAFF_OF_POWER = null;

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemProxysStaffOfPower(new Item.Properties().group(CreepyCreepers.CREATIVE_TAB).maxStackSize(1)).setRegistryName(CreepyCreepers.MODID, "proxys_staff_of_power"));
	}
}
