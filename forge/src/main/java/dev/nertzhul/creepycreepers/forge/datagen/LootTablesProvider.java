package dev.nertzhul.creepycreepers.forge.datagen;

import dev.nertzhul.creepycreepers.setup.CcEntities;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LootTablesProvider extends LootTableProvider {
    public LootTablesProvider(PackOutput output) {
        super(output, Collections.emptySet(), List.of(
            new SubProviderEntry(EntitySubProvider::new, LootContextParamSets.ENTITY)
        ));
    }
    
    public static class EntitySubProvider extends EntityLootSubProvider {
        private final Set<EntityType<?>> knownEntityTypes = new ReferenceOpenHashSet<>();
        
        protected EntitySubProvider() {
            super(FeatureFlags.VANILLA_SET);
        }
        
        @Override
        public void generate() {
            add(CcEntities.GHOSTLY_CREEPER.get(), baseCreeperLoot());
            add(CcEntities.SNOWY_CREEPER.get(), baseCreeperLoot()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.SNOWBALL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))))
            );
            add(CcEntities.HALLOWEEN_CREEPER.get(), baseCreeperLoot());
        }
        
        private LootTable.Builder baseCreeperLoot() {
            return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(Items.GUNPOWDER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                    )
                )
                .withPool(LootPool.lootPool()
                    .add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
                    .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))
                );
        }
        
        @Override @NotNull
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return this.knownEntityTypes.stream();
        }
        
        @Override
        protected void add(@NotNull EntityType<?> entityType, LootTable.@NotNull Builder builder) {
            super.add(entityType, builder);
            this.knownEntityTypes.add(entityType);
        }
    }
}
