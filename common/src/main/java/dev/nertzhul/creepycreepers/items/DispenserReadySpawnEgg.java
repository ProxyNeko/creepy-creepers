package dev.nertzhul.creepycreepers.items;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.DispenserBlock;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DispenserReadySpawnEgg extends SpawnEggItem {
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    
    public static final List<Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem>> SPAWN_EGGS = new ArrayList<>();
    private final Supplier<? extends EntityType<? extends Mob>> entityType;
    
    public DispenserReadySpawnEgg(Supplier<? extends EntityType<? extends Mob>> typeIn, int backgroundColor, int highlightColor, Properties properties) {
        super(null, backgroundColor, highlightColor, properties);
        this.entityType = typeIn;
        
        setupDispenserBehavior();
        SPAWN_EGGS.add(new Pair<>(typeIn, this));
    }
    
    protected void setupDispenserBehavior() {
        DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
            public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                Direction direction = source.state().getValue(DispenserBlock.FACING);
                
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack);
                entitytype.spawn(source.level(), stack, null,
                    source.pos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                
                stack.shrink(1);
                return stack;
            }
        });
    }
    
    @Override @NotNull
    public EntityType<?> getType(@NotNull ItemStack stack) {
        final CustomData data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        return !data.isEmpty() ? data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(this.entityType.get()) : this.entityType.get();
    }
    
    @Override @NotNull
    public FeatureFlagSet requiredFeatures() {
        return getType(ItemStack.EMPTY).requiredFeatures();
    }
}
