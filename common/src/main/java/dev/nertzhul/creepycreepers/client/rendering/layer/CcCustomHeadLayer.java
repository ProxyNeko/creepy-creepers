package dev.nertzhul.creepycreepers.client.rendering.layer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.nertzhul.creepycreepers.client.rendering.CcHeadedCreeperModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

import java.util.Map;

public class CcCustomHeadLayer<T extends Creeper, M extends CcHeadedCreeperModel<T>> extends RenderLayer<T, M> {
    private final Map<SkullBlock.Type, SkullModelBase> skullModels;
    private final ItemInHandRenderer itemInHandRenderer;
    
    public CcCustomHeadLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.skullModels = SkullBlockRenderer.createSkullRenderers(modelSet);
        this.itemInHandRenderer = itemInHandRenderer;
    }
    
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty()) {
            Item item = itemStack.getItem();
            poseStack.pushPose();
            poseStack.scale(0.9F, 0.9F, 0.9F);
            if (livingEntity.isBaby()) {
                poseStack.translate(0.0F, 0.03125F, 0.0F);
                poseStack.scale(0.7F, 0.7F, 0.7F);
                poseStack.translate(0.0F, 1.0F, 0.0F);
            }
            
            this.getParentModel().getHead().translateAndRotate(poseStack);
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
                poseStack.scale(1.1875F, -1.1875F, -1.1875F);
                
                GameProfile gameProfile = null;
                if (itemStack.hasTag()) {
                    CompoundTag compoundTag = itemStack.getTag();
                    if (compoundTag.contains("SkullOwner", 10)) {
                        gameProfile = NbtUtils.readGameProfile(compoundTag.getCompound("SkullOwner"));
                    }
                }
                
                poseStack.translate(-0.5, -0.05, -0.5);
                
                SkullBlock.Type type = ((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType();
                SkullModelBase skullModelBase = this.skullModels.get(type);
                RenderType renderType = SkullBlockRenderer.getRenderType(type, gameProfile);
                Entity var22 = livingEntity.getVehicle();
                
                WalkAnimationState walkAnimationState;
                if (var22 instanceof LivingEntity livingEntity2) {
                    walkAnimationState = livingEntity2.walkAnimation;
                } else {
                    walkAnimationState = livingEntity.walkAnimation;
                }
                
                float h = walkAnimationState.position(partialTick);
                SkullBlockRenderer.renderSkull(null, 180.0F, h, poseStack, buffer, packedLight, skullModelBase, renderType);
            } else {
                label60: {
                    if (item instanceof ArmorItem armorItem) {
                        if (armorItem.getEquipmentSlot() == EquipmentSlot.HEAD) {
                            break label60;
                        }
                    }
                    translateToHead(poseStack);
                    this.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
                }
            }
            poseStack.popPose();
        }
    }
    
    public static void translateToHead(PoseStack poseStack) {
        float f = 0.625F;
        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(f, -f, -f);
    }
}
