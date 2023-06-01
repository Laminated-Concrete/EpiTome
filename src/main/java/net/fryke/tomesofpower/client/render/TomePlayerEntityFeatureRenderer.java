package net.fryke.tomesofpower.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class TomePlayerEntityFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    public static final SpriteIdentifier BOOK_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/enchanting_table_book"));
    private final BookModel firstBook;
    private final BookModel secondBook;
    private final BookModel thirdBook;
    private final BookModel fourthBook;
    private final BookModel fifthBook;
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float flipRandom;
    public float flipTurn;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    public float bookRotation;
    public float lastBookRotation;
    public float targetBookRotation;
    private static final Random RANDOM = Random.create();

    public TomePlayerEntityFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context, EntityModelLoader loader) {
        super(context);
        this.firstBook = new BookModel(loader.getModelPart(EntityModelLayers.BOOK));
        this.secondBook = new BookModel(loader.getModelPart(EntityModelLayers.BOOK));
        this.thirdBook = new BookModel(loader.getModelPart(EntityModelLayers.BOOK));
        this.fourthBook = new BookModel(loader.getModelPart(EntityModelLayers.BOOK));
        this.fifthBook = new BookModel(loader.getModelPart(EntityModelLayers.BOOK));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        matrices.push();
        renderFirstBook(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        renderSecondBook(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        renderThirdBook(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        renderFourthBook(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        renderFifthBook(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        matrices.pop();

        nextPageTurningSpeed = MathHelper.clamp(nextPageTurningSpeed, 0.0f, 1.0f);
        ++ticks;
        pageAngle = nextPageAngle;
        float h1 = (flipRandom - nextPageAngle) * 0.4f;
        float i = 0.2f;
        h1 = MathHelper.clamp(h1, -0.2f, 0.2f);
        flipTurn += (h1 - flipTurn) * 0.9f;
        nextPageAngle += flipTurn;
        pageTurningSpeed = nextPageTurningSpeed;

        // Rotation things?
//        lastBookRotation = bookRotation;
//        PlayerEntity playerEntity = MinecraftClient.getInstance().player;
//        if (playerEntity != null) {
//            double d = playerEntity.getX() - ((double)getX() + 0.5);
//            double e = playerEntity.getZ() - ((double)getZ() + 0.5);
//            targetBookRotation = (float) MathHelper.atan2(e, d);
//            nextPageTurningSpeed += 0.1f;
//            if (nextPageTurningSpeed < 0.5f || RANDOM.nextInt(40) == 0) {
//                float f = flipRandom;
//                do {
//                    flipRandom += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
//                } while (f == flipRandom);
//            }
//        } else {
//            targetBookRotation += 0.02f;
//            nextPageTurningSpeed -= 0.1f;
//        }
//        while (bookRotation >= (float)Math.PI) {
//            bookRotation -= (float)Math.PI * 2;
//        }
//        while (bookRotation < (float)(-Math.PI)) {
//            bookRotation += (float)Math.PI * 2;
//        }
//        while (targetBookRotation >= (float)Math.PI) {
//            targetBookRotation -= (float)Math.PI * 2;
//        }
//        while (targetBookRotation < (float)(-Math.PI)) {
//            targetBookRotation += (float)Math.PI * 2;
//        }
//        for (g = targetBookRotation - bookRotation; g >= (float)Math.PI; g -= (float)Math.PI * 2) {
//        }
//        while (g < (float)(-Math.PI)) {
//            g += (float)Math.PI * 2;
//        }
//        bookRotation += g * 0.4f;
    }

    private void renderFirstBook(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // THis book is in the casting position
        // Books are 6 pixels wide, which is 6/16 or 0.375 of a block
        float h;
        matrices.translate(0.1875f, 0f, -0.75f);
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-16f));

        float pageTurnAmount = (float)ticks + tickDelta;
        matrices.translate(0.0f, 0.1f + MathHelper.sin(pageTurnAmount * 0.1f) * 0.01f, 0.0f);
        for (h = bookRotation - lastBookRotation; h >= (float)Math.PI; h -= (float)Math.PI * 2) {
        }
        while (h < (float)(-Math.PI)) {
            h += (float)Math.PI * 2;
        }
        float k = lastBookRotation + h * tickDelta;
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-k)); // meant to rotate the book towards the player
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0f)); // makes the book lean back when opened
        float l = MathHelper.lerp(tickDelta, pageAngle, nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25f) * 1.6f - 0.3f;
        float n = MathHelper.fractionalPart(l + 0.75f) * 1.6f - 0.3f;
        float pageTurnSpeed = MathHelper.lerp(tickDelta, pageTurningSpeed, nextPageTurningSpeed);
        this.firstBook.setPageAngles(pageTurnAmount, MathHelper.clamp(m, 0.0f, 1.0f), MathHelper.clamp(n, 0.0f, 1.0f), pageTurnSpeed);

        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        this.firstBook.renderBook(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        // reset back to center
        matrices.translate(-0.1875f, 0f, 0.75f);
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(16f));
    }

    private void renderSecondBook(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // This book is to the right of the first
        float h;
        // from center
        matrices.translate(-0.75f - 0.1875f, 0f, 0.5f);
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-16f));

        float pageTurnAmount = 1f; //(float)ticks + tickDelta;
        matrices.translate(0.0f, 0.1f + MathHelper.sin(pageTurnAmount * 0.1f) * 0.01f, 0.0f);
        for (h = bookRotation - lastBookRotation; h >= (float)Math.PI; h -= (float)Math.PI * 2) {
        }
        while (h < (float)(-Math.PI)) {
            h += (float)Math.PI * 2;
        }
//        float k = lastBookRotation + h * tickDelta;
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-k)); // meant to rotate the book towards the player
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0f)); // makes the book lean back when opened
        float l = MathHelper.lerp(tickDelta, pageAngle, nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25f) * 1.6f - 0.3f;
        float n = MathHelper.fractionalPart(l + 0.75f) * 1.6f - 0.3f;
        float pageTurnSpeed = MathHelper.lerp(tickDelta, pageTurningSpeed, nextPageTurningSpeed);
        this.secondBook.setPageAngles(pageTurnAmount, MathHelper.clamp(m, 0.0f, 1.0f), MathHelper.clamp(n, 0.0f, 1.0f), pageTurnSpeed);

        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        this.secondBook.renderBook(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderThirdBook(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // This book is to the right of the second
        matrices.translate(0.5f, 0f, 0.25f);

        float h;
        float pageTurnAmount = 1f; //(float)ticks + tickDelta;
        matrices.translate(0.0f, 0.1f + MathHelper.sin(pageTurnAmount * 0.1f) * 0.01f, 0.0f);
        for (h = bookRotation - lastBookRotation; h >= (float)Math.PI; h -= (float)Math.PI * 2) {
        }
        while (h < (float)(-Math.PI)) {
            h += (float)Math.PI * 2;
        }
        float k = lastBookRotation + h * tickDelta;
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-k)); // meant to rotate the book towards the player
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0f)); // makes the book lean back when opened
        float l = MathHelper.lerp(tickDelta, pageAngle, nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25f) * 1.6f - 0.3f;
        float n = MathHelper.fractionalPart(l + 0.75f) * 1.6f - 0.3f;
        float pageTurnSpeed = MathHelper.lerp(tickDelta, pageTurningSpeed, nextPageTurningSpeed);
        this.thirdBook.setPageAngles(pageTurnAmount, MathHelper.clamp(m, 0.0f, 1.0f), MathHelper.clamp(n, 0.0f, 1.0f), pageTurnSpeed);

        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        this.thirdBook.renderBook(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderFourthBook(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // This book is to the right of the third
        matrices.translate(0.5f, 0f, 0f);

        float h;
        float pageTurnAmount = 1f; //(float)ticks + tickDelta;
        matrices.translate(0.0f, 0.1f + MathHelper.sin(pageTurnAmount * 0.1f) * 0.01f, 0.0f);
        for (h = bookRotation - lastBookRotation; h >= (float)Math.PI; h -= (float)Math.PI * 2) {
        }
        while (h < (float)(-Math.PI)) {
            h += (float)Math.PI * 2;
        }
        float k = lastBookRotation + h * tickDelta;
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-k)); // meant to rotate the book towards the player
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0f)); // makes the book lean back when opened
        float l = MathHelper.lerp(tickDelta, pageAngle, nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25f) * 1.6f - 0.3f;
        float n = MathHelper.fractionalPart(l + 0.75f) * 1.6f - 0.3f;
        float pageTurnSpeed = MathHelper.lerp(tickDelta, pageTurningSpeed, nextPageTurningSpeed);
        this.fourthBook.setPageAngles(pageTurnAmount, MathHelper.clamp(m, 0.0f, 1.0f), MathHelper.clamp(n, 0.0f, 1.0f), pageTurnSpeed);

        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        this.fourthBook.renderBook(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderFifthBook(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // This book is to the right of the fourth, left of the first
        matrices.translate(0.5f, 0f, -0.25f);

        float h;
        float pageTurnAmount = 1f; //(float)ticks + tickDelta;
        matrices.translate(0.0f, 0.1f + MathHelper.sin(pageTurnAmount * 0.1f) * 0.01f, 0.0f);
        for (h = bookRotation - lastBookRotation; h >= (float)Math.PI; h -= (float)Math.PI * 2) {
        }
        while (h < (float)(-Math.PI)) {
            h += (float)Math.PI * 2;
        }
        float k = lastBookRotation + h * tickDelta;
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-k)); // meant to rotate the book towards the player
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(80.0f)); // makes the book lean back when opened
        float l = MathHelper.lerp(tickDelta, pageAngle, nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25f) * 1.6f - 0.3f;
        float n = MathHelper.fractionalPart(l + 0.75f) * 1.6f - 0.3f;
        float pageTurnSpeed = MathHelper.lerp(tickDelta, pageTurningSpeed, nextPageTurningSpeed);
        this.fifthBook.setPageAngles(pageTurnAmount, MathHelper.clamp(m, 0.0f, 1.0f), MathHelper.clamp(n, 0.0f, 1.0f), pageTurnSpeed);

        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        this.fifthBook.renderBook(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
