package dev.nertzhul.creepycreepers.client.rendering.layer;

import dev.nertzhul.creepycreepers.client.rendering.CcCreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

import org.jetbrains.annotations.NotNull;

public class CcCreeperPowerLayer<T extends Creeper, M extends CcCreeperModel<T>> extends EnergySwirlLayer<T, M> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CcCreeperModel<T> model;
    
    public CcCreeperPowerLayer(RenderLayerParent<T, M> pRenderer, CcCreeperModel<T> pModel) {
        super(pRenderer);
        this.model = pModel;
    }
    
    @Override
    protected float xOffset(float tickCount) {
        return tickCount * 0.005F;
    }
    
    @Override @NotNull
    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }
    
    @Override @NotNull
    protected EntityModel<T> model() {
        return this.model;
    }
}
