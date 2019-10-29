package com.mcmoddev.spookyjam.common;

import com.mcmoddev.spookyjam.CreepyCreepers;
import com.mcmoddev.spookyjam.common.entities.GhostlyCreeperEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = CreepyCreepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {

    public static final EntityType<GhostlyCreeperEntity> GHOSTLY_CREEPER_ENTITY = (EntityType<GhostlyCreeperEntity>) EntityType.Builder.create(GhostlyCreeperEntity::new, EntityClassification.MONSTER)
            .size(0.6F, 1.7F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .setShouldReceiveVelocityUpdates(true)
            .build(CreepyCreepers.MODID + ":ghostly_creeper")
            .setRegistryName(new ResourceLocation(CreepyCreepers.MODID, "ghostly_creeper"));

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                GHOSTLY_CREEPER_ENTITY
        );
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new SpawnEggItem(GHOSTLY_CREEPER_ENTITY, Color.WHITE.getRGB(), Color.WHITE.getRGB(),
                        new Item.Properties().group(CreepyCreepers.CREATIVE_TAB)).setRegistryName(CreepyCreepers.MODID + ":ghostly_creeper_spawn_egg")
        );
    }
}
