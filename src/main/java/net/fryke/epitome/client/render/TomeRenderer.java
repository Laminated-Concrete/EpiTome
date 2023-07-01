package net.fryke.epitome.client.render;

import net.fryke.epitome.client.SpellPageAnimatable;
import net.fryke.epitome.client.model.SpellPageLayer;
import net.fryke.epitome.client.model.TomeModel;
import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TomeRenderer extends GeoItemRenderer<TomeItem> {
    public TomeRenderer(SpellPageAnimatable spellPageAnimatable) {
        super(new TomeModel());
        addRenderLayer(new SpellPageLayer(spellPageAnimatable));
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
        float windowWidth = MinecraftClient.getInstance().getWindow().getWidth();
        float windowHeight = MinecraftClient.getInstance().getWindow().getHeight();
        float aspectRatio = windowWidth / windowHeight;

        // this bit adjusts the position of the tome in-hand if the aspect ratio is a problem
        // we need to do this since we want the player to always see the page of the tome
        if(aspectRatio < 1.65f) {
            float offset = -1 + (aspectRatio / 1.65f);
            if(transformType == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) {
                poseStack.push();
                poseStack.translate(offset, 0, 0);
                super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
                poseStack.pop();
            } else if(transformType == ModelTransformationMode.FIRST_PERSON_LEFT_HAND) {
                poseStack.push();
                poseStack.translate(-offset, 0, 0);
                super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
                poseStack.pop();
            } else {
                super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
            }
        } else {
            super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }
}
