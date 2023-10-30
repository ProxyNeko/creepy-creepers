package dev.nertzhul.creepycreepers.client.rendering.corruptedcreeper;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCreeperPowerLayer;
import dev.nertzhul.creepycreepers.entities.CorruptedCreeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class CorruptedCreeperRenderer extends CcCreeperRenderer<CorruptedCreeper, CorruptedCreeperModel> {
    private static final ResourceLocation TEXTURE = CreepyCreepers.resource("textures/entity/corrupted_creeper.png");
    
    public CorruptedCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CorruptedCreeperModel(pContext.bakeLayer(CorruptedCreeperModel.LAYER)), 0.5F);
        this.addLayer(new CcCreeperPowerLayer<>(this, new CorruptedCreeperModel(pContext.getModelSet().bakeLayer(CorruptedCreeperModel.ARMOR_LAYER))));
    }
    
    @Override @NotNull
    public ResourceLocation getTextureLocation(CorruptedCreeper entity) {
        return TEXTURE;
    }
}
