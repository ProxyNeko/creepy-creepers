package dev.nertzhul.creepycreepers.client.rendering;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Creeper;

public abstract class CcHeadedCreeperRenderer<T extends Creeper, M extends CcHeadedCreeperModel<T>> extends CcCreeperRenderer<T, M> {
    protected CcHeadedCreeperRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }
}
