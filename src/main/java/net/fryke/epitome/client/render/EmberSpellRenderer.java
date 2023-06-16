package net.fryke.epitome.client.render;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.client.model.EmberSpellEntityModel;
import net.fryke.epitome.entity.spell.projectile.EmberSpellEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EmberSpellRenderer extends GeoEntityRenderer<EmberSpellEntity> {
    public EmberSpellRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EmberSpellEntityModel());
    }

    @Override
    public Identifier getTextureLocation(EmberSpellEntity animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "textures/entity/ember_spell.png");
    }

    @Override
    public void render(EmberSpellEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
