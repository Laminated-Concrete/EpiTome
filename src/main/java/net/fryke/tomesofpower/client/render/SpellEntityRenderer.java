package net.fryke.tomesofpower.client.render;

import net.fryke.tomesofpower.spells.types.ProjectileSpellEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


public class SpellEntityRenderer extends EntityRenderer<ProjectileSpellEntity>  {
    public static final ItemStack STACK = new ItemStack(Items.TNT);

    public SpellEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ProjectileSpellEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                STACK,
                ModelTransformationMode.FIXED,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                null,
                0
        );
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ProjectileSpellEntity entity) {
        // this is for when you are rendering things like a cow
        //  In there, the texture is passed up and attached to the CowEntityModel
        //  because we aren't using a model, we just return null
        return null;
    }
}
