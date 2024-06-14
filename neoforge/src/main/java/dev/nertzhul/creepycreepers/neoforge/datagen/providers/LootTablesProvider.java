package dev.nertzhul.creepycreepers.neoforge.datagen.providers;

import dev.nertzhul.creepycreepers.setup.CcEntities;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class LootTablesProvider extends LootTableProvider {
    public LootTablesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Collections.emptySet(), List.of(
            new SubProviderEntry(EntitySubProvider::new, LootContextParamSets.ENTITY)
        ), provider);
    }
    
    public static class EntitySubProvider extends EntityLootSubProvider {
        private final Set<EntityType<?>> knownEntityTypes = new ReferenceOpenHashSet<>();
        
        protected EntitySubProvider() {
            super(FeatureFlags.VANILLA_SET);
        }
        
        @Override
        public void generate() {
            add(CcEntities.CORRUPTED_CREEPER.get(), baseCreeperLoot());
            add(CcEntities.GHOSTLY_CREEPER.get(), baseCreeperLoot());
            add(CcEntities.HALLOWEEN_CREEPER.get(), baseCreeperLoot());
            add(CcEntities.SNOWY_CREEPER.get(), baseCreeperLoot()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.SNOWBALL).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))))
            );
            add(CcEntities.TUFF_CREEPER.get(), baseCreeperLoot()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.TUFF).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(-2.0F, 3.0F))))
                    .add(LootItem.lootTableItem(Items.RAW_IRON).apply(SetItemCountFunction.setCount(UniformGenerator.between(-3.0F, 3.0F))))
                    .add(LootItem.lootTableItem(Items.RAW_COPPER).apply(SetItemCountFunction.setCount(UniformGenerator.between(-3.0F, 3.0F))))
                    .add(LootItem.lootTableItem(Items.RAW_GOLD).apply(SetItemCountFunction.setCount(UniformGenerator.between(-3.0F, 3.0F))))
                    .add(LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(-4.0F, 4.0F))))
                    .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(-5.0F, 2.0F))))
                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 0.75F)))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.DIAMOND))
                    .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.01F, 0.01F))
                )
            );
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
