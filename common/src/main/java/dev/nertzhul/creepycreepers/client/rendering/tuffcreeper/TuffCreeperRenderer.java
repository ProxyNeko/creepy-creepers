package dev.nertzhul.creepycreepers.client.rendering.tuffcreeper;

import dev.nertzhul.creepycreepers.CreepyCreepers;
import dev.nertzhul.creepycreepers.client.rendering.CcCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCreeperPowerLayer;
import dev.nertzhul.creepycreepers.entities.TuffCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class TuffCreeperRenderer extends CcCreeperRenderer<TuffCreeper, TuffCreeperModel> {
    private static final ResourceLocation TEXTURE = CreepyCreepers.resource("textures/entity/tuff_creeper.png");
    
    public TuffCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TuffCreeperModel(pContext.bakeLayer(TuffCreeperModel.LAYER)), 0.5F);
        this.addLayer(new CcCreeperPowerLayer<>(this, new TuffCreeperModel(pContext.getModelSet().bakeLayer(ModelLayers.CREEPER_ARMOR))));
    }
    
    @Override @NotNull
    public ResourceLocation getTextureLocation(TuffCreeper entity) {
        return TEXTURE;
    }
}
