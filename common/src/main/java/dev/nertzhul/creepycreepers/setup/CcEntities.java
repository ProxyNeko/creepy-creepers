package dev.nertzhul.creepycreepers.setup;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.entities.GhostlyCreeper;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import dev.nertzhul.creepycreepers.util.registry.RegistryEntry;
import dev.nertzhul.creepycreepers.util.registry.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CcEntities {
    public static final RegistryProvider<EntityType<?>> ENTITIES = RegistryProvider.create(Registries.ENTITY_TYPE, CreepyCreepers.MOD_ID);
    
    public static final RegistryEntry<EntityType<GhostlyCreeper>> GHOSTLY_CREEPER = register("ghostly_creeper",
        EntityType.Builder.of(GhostlyCreeper::new, MobCategory.MONSTER).sized(0.6F, 1.8F));
    public static final RegistryEntry<EntityType<SnowyCreeper>> SNOWY_CREEPER = register("snowy_creeper",
        EntityType.Builder.of(SnowyCreeper::new, MobCategory.MONSTER).sized(0.6F, 1.8F));
    
    private static <T extends Entity> RegistryEntry<EntityType<T>> register(String pName, EntityType.Builder<T> pBuilder) {
        return ENTITIES.register(pName, () -> pBuilder.build(CreepyCreepers.resource(pName).toString()));
    }
}
