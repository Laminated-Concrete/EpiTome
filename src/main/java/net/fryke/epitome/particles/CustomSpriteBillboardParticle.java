package net.fryke.epitome.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;

/**
 * A {@link CustomBillboardParticle} implementation class that renders a {@link Sprite} as its camera-facing texture.
 */
@Environment(value=EnvType.CLIENT)
public abstract class CustomSpriteBillboardParticle extends CustomBillboardParticle {
    protected Sprite sprite;

    protected CustomSpriteBillboardParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    protected CustomSpriteBillboardParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, g, h, i);
    }

    /**
     * Sets the current {@link Sprite} of this particle.
     *
     * <p>
     * To assign a {@link Sprite} based on particle age, see {@link #setSpriteForAge}.
     *
     * @param sprite the new {@link Sprite} to assign to this {@link CustomAbstractParticle}
     */
    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    protected float getMinU() {
        return this.sprite.getMinU();
    }

    @Override
    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    @Override
    protected float getMinV() {
        return this.sprite.getMinV();
    }

    @Override
    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    /**
     * Sets the current {@link Sprite} of this particle to a random frame in its atlas sheet.
     *
     * @param spriteProvider sprite access for retrieving random {@link Sprite} frames
     */
    public void setSprite(SpriteProvider spriteProvider) {
        // TODO this might be bad?
        Random tempRandom = Random.create();
        this.setSprite(spriteProvider.getSprite(tempRandom));
    }

    /**
     * Sets the current {@link Sprite} of this particle based on the age of the particle, assuming the particle texture is an atlas with multiple frames.
     *
     * @param spriteProvider sprite access for retrieving the proper {@link Sprite} based on lifetime progress
     */
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            this.setSprite(spriteProvider.getSprite(this.age, this.maxAge));
        }
    }
}

