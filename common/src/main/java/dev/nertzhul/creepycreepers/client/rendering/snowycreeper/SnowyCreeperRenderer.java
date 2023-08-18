package dev.nertzhul.creepycreepers.client.rendering.snowycreeper;

import dev.nertzhul.creepycreepers.client.rendering.CcCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCreeperPowerLayer;
import dev.nertzhul.creepycreepers.entities.SnowyCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SnowyCreeperRenderer extends CcCreeperRenderer<SnowyCreeper, SnowyCreeperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/creeper/creeper.png");
    
    public SnowyCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SnowyCreeperModel(pContext.bakeLayer(SnowyCreeperModel.LAYER)), 0.5F);
        this.addLayer(new CcCreeperPowerLayer<>(this, new SnowyCreeperModel(pContext.getModelSet().bakeLayer(ModelLayers.CREEPER_ARMOR))));
    }
    
    @Override @NotNull
    public ResourceLocation getTextureLocation(SnowyCreeper entity) {
        return TEXTURE;
    }
}
