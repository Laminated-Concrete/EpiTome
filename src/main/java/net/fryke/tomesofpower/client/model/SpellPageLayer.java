package net.fryke.tomesofpower.client.model;

import net.fryke.tomesofpower.client.SpellPageAnimatable;
import net.fryke.tomesofpower.client.render.SpellPageLayerRenderer;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SpellPageLayer extends GeoRenderLayer<TomeItem> {
    public SpellPageAnimatable spellPageAnimatable;
    private SpellPageLayerRenderer testRenderer = new SpellPageLayerRenderer();

    public SpellPageLayer(SpellPageAnimatable spellPageAnimatable) {
        super(new SpellPageLayerRenderer()); // this is automatically passing in the tomeItem type into the renderer, so the renderer expects tomeTime animatables
        this.spellPageAnimatable = spellPageAnimatable;
    }

    @Override
    public void render(MatrixStack poseStack, TomeItem animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        poseStack.push();

        Identifier textureId = testRenderer.getTextureLocation(spellPageAnimatable);
        RenderLayer spellPageRenderType = RenderLayer.getEntityCutoutNoCull(textureId);
        BakedGeoModel actualBakedModel = testRenderer.getGeoModel().getBakedModel(testRenderer.getGeoModel().getModelResource(spellPageAnimatable));

        testRenderer.actuallyRender(poseStack, spellPageAnimatable, actualBakedModel, spellPageRenderType, bufferSource, bufferSource.getBuffer(spellPageRenderType),
                false, partialTick, packedLight, packedOverlay, 1, 1, 1, 1);

        poseStack.pop();
    }
}
