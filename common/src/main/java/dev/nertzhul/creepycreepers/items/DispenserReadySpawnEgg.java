package dev.nertzhul.creepycreepers.items;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DispenserReadySpawnEgg extends SpawnEggItem {
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
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getLevel(), stack, null,
                    source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                
                stack.shrink(1);
                return stack;
            }
        });
    }
    
    @Override @NotNull
    public EntityType<?> getType(@Nullable CompoundTag compoundTag) {
        if (compoundTag != null && compoundTag.contains("EntityTag", 10)) {
            CompoundTag compoundTag2 = compoundTag.getCompound("EntityTag");
            if (compoundTag2.contains("id", 8)) {
                return EntityType.byString(compoundTag2.getString("id")).orElseGet(this.entityType);
            }
        }
        return this.entityType.get();
    }
    
    @Override @NotNull
    public FeatureFlagSet requiredFeatures() {
        return getType(null).requiredFeatures();
    }
}
