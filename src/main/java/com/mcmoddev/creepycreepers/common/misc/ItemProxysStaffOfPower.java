package com.mcmoddev.creepycreepers.common.misc;

import com.mcmoddev.creepycreepers.CreepyCreepers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemProxysStaffOfPower extends Item {

	public ItemProxysStaffOfPower(Properties properties) {
		super(properties);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nonnull World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		new TranslationTextComponent("easter_egg_item_information");
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (!world.isRemote) {
			if (hand == Hand.MAIN_HAND) {
				double x, y, z;
				x = player.func_213324_a(150, 1, true).getHitVec().getX();
				y = player.func_213324_a(150, 1, true).getHitVec().getY();
				z = player.func_213324_a(150, 1, true).getHitVec().getZ();
				world.addEntity(new LightningBoltEntity(world, x, y, z, false));
			}
		}
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}
}
