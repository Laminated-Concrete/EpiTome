package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.ReceptacleBlockEntityModel;
import net.fryke.epitome.client.model.ReceptacleTomeLayer;
import net.fryke.epitome.entity.ReceptacleBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ReceptacleBlockEntityRenderer extends GeoBlockRenderer<ReceptacleBlockEntity> {
    public ReceptacleBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new ReceptacleBlockEntityModel());
        addRenderLayer(new ReceptacleTomeLayer());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, ReceptacleBlockEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        // so the idea is we somehow know that an event has been triggered, and then we make a particle here?
        // oof. we get the world from the animatable
        // if we want this to be dynamic, then we need to know what event was passed
    }
}
