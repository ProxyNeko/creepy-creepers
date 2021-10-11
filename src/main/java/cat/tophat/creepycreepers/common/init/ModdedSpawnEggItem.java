package cat.tophat.creepycreepers.common.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Exists to work around a limitation with Spawn Eggs:
 * Spawn Eggs require an EntityType, but EntityTypes are created AFTER Items.
 * Therefore it is "impossible" for modded spawn eggs to exist.
 * This class gets around it by passing "null" to the SpawnEggItem constructor
 * and doing the initialisation after registry events have finished firing.
 * <p>
 * TODO: Remove once Forge adds this stuff in itself.
 *
 * @author Cadiboo
 */
public class ModdedSpawnEggItem extends SpawnEggItem {

	protected static final List<ModdedSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public ModdedSpawnEggItem(final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, final int primaryColorIn, final int secondaryColorIn, final Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public ModdedSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final int primaryColorIn, final int secondaryColorIn, final Properties builder) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
		UNADDED_EGGS.add(this);
	}

	/**
	 * Adds all the supplier based spawn eggs to vanilla's map and registers an
	 * IDispenseItemBehavior for each of them as normal spawn eggs have one
	 * registered for each of them during {@link DispenseItemBehavior#bootStrap()}
	 * but supplier based ones won't have had their EntityTypes created yet.
	 */
	public static void initUnaddedEggs() {
		final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "BY_ID");
		DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};
		for (final SpawnEggItem egg : UNADDED_EGGS) {
			EGGS.put(egg.getType(null), egg);
			DispenserBlock.registerBehavior(egg, defaultDispenseItemBehavior);
			// ItemColors for each spawn egg don't need to be registered because this method is called before ItemColors is created
		}
		UNADDED_EGGS.clear();
	}

	@Override
	public EntityType<?> getType(@Nullable final CompoundTag p_208076_1_) {
		return entityTypeSupplier.get();
	}

}
