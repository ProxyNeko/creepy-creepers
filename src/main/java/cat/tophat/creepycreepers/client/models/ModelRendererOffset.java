package cat.tophat.creepycreepers.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;

/**
 * An extension of the {@link ModelRenderer} class
 * to allow for offsets in translation to occur without
 * affecting the original position.
 */
public class ModelRendererOffset extends ModelPart {

	public float offsetX, offsetY, offsetZ;

	public ModelRendererOffset(Model model) {
		super(model);
	}

	public ModelRendererOffset(Model model, int texOffX, int texOffY) {
		super(model, texOffX, texOffY);
	}

	public ModelRendererOffset(int textureWidthIn, int textureHeightIn, int textureOffsetXIn, int textureOffsetYIn) {
		super(textureWidthIn, textureHeightIn, textureOffsetXIn, textureOffsetYIn);
	}

	@Override
	public void translateAndRotate(PoseStack matrixStackIn) {
		matrixStackIn.translate(this.offsetX, this.offsetY, this.offsetZ);
		super.translateAndRotate(matrixStackIn);
	}
}
