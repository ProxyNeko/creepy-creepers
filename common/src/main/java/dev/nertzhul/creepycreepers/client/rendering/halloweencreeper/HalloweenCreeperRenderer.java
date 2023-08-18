package dev.nertzhul.creepycreepers.client.rendering.halloweencreeper;

import dev.nertzhul.creepycreepers.client.rendering.CcHeadedCreeperRenderer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCreeperPowerLayer;
import dev.nertzhul.creepycreepers.client.rendering.layer.CcCustomHeadLayer;
import dev.nertzhul.creepycreepers.entities.HalloweenCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HalloweenCreeperRenderer extends CcHeadedCreeperRenderer<HalloweenCreeper, HalloweenCreeperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/creeper/creeper.png");
    
    public HalloweenCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HalloweenCreeperModel(pContext.bakeLayer(HalloweenCreeperModel.LAYER)), 0.5F);
        this.addLayer(new CcCreeperPowerLayer<>(this, new HalloweenCreeperModel(pContext.getModelSet().bakeLayer(ModelLayers.CREEPER_ARMOR))));
        this.addLayer(new CcCustomHeadLayer<>(this, pContext.getModelSet(), pContext.getItemInHandRenderer()));
    }
    
    @Override @NotNull
    public ResourceLocation getTextureLocation(HalloweenCreeper entity) {
        return TEXTURE;
    }
}
