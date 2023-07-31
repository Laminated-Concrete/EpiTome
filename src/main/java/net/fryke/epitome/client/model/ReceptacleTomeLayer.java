package net.fryke.epitome.client.model;

import net.fryke.epitome.client.render.ReceptacleTomeLayerRenderer;
import net.fryke.epitome.entity.ReceptacleBlockEntity;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ReceptacleTomeLayer extends GeoRenderLayer<ReceptacleBlockEntity> {
    private final ReceptacleTomeLayerRenderer receptacleTomeLayerRenderer = new ReceptacleTomeLayerRenderer();

    public ReceptacleTomeLayer() {
        super(new ReceptacleTomeLayerRenderer());
    }

    @Override
    public void render(MatrixStack poseStack, ReceptacleBlockEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        Identifier tomeIdentifier = animatable.getHeldTomeIdentifier();

        if(tomeIdentifier != null && tomeIdentifier.getNamespace().equals("epitome") && !tomeIdentifier.getPath().equals("null")) {
            Item possibleTome = Registries.ITEM.get(tomeIdentifier);
            if(possibleTome instanceof TomeItem) {
                TomeItem tomeItem = (TomeItem) possibleTome;

                poseStack.push();
                poseStack.translate(0.5, 1.4, 0.5);

                float tick = (float)animatable.getTick(animatable) + partialTick;
                poseStack.translate(0.0f, 0.1f + MathHelper.sin(tick * 0.1f) * 0.02f, 0.0f);

                float rotationSpeed = 1.0f;
                float angle = MathHelper.wrapDegrees(tick * rotationSpeed); // determines what angle we should be at based on tick
                poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle)); // slowly rotates the book around the Y axis
                poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(80.0f)); // tilts the book so it's leaning upright

                Identifier textureId = receptacleTomeLayerRenderer.getTextureLocation(tomeItem);
                RenderLayer receptacleTomeLayerRenderType = RenderLayer.getEntityCutoutNoCull(textureId);
                BakedGeoModel actualBakedModel = receptacleTomeLayerRenderer.getGeoModel().getBakedModel(receptacleTomeLayerRenderer.getGeoModel().getModelResource(tomeItem));

                receptacleTomeLayerRenderer.actuallyRender(poseStack, tomeItem, actualBakedModel, receptacleTomeLayerRenderType, bufferSource, bufferSource.getBuffer(receptacleTomeLayerRenderType),
                    false, partialTick, packedLight, packedOverlay, 1, 1, 1, 1);

                poseStack.pop();
            }
        }
    }
}
