package dev.nertzhul.creepycreepers.client.rendering;

import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

import java.util.function.Function;

public abstract class CcHeadedCreeperModel<T extends Creeper> extends CcCreeperModel<T> implements HeadedModel {
    protected CcHeadedCreeperModel(ModelPart pRoot) {
        this(pRoot, RenderType::entityCutoutNoCull);
    }
    
    protected CcHeadedCreeperModel(ModelPart pRoot, Function<ResourceLocation, RenderType> renderType) {
        super(pRoot, renderType);
    }
}
