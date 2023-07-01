package net.fryke.epitome.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

@Environment(value=EnvType.CLIENT)
public class CustomAscendingParticle extends CustomSpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected CustomAscendingParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        float f;
        this.velocityMultiplier = 0.96f;
        this.gravityStrength = gravityStrength;
        this.field_28787 = true;
        this.spriteProvider = spriteProvider;
        this.velocityX *= (double)randomVelocityXMultiplier;
        this.velocityY *= (double)randomVelocityYMultiplier;
        this.velocityZ *= (double)randomVelocityZMultiplier;
        this.velocityX += velocityX;
        this.velocityY += velocityY;
        this.velocityZ += velocityZ;
        this.red = f = random.nextFloat() * colorMultiplier;
        this.green = f;
        this.blue = f;
        this.scale *= 0.75f * scaleMultiplier;
        this.maxAge = (int)((double)baseMaxAge / ((double)random.nextFloat() * 0.8 + 0.2) * (double)scaleMultiplier);
        this.maxAge = Math.max(this.maxAge, 1);
        this.setSpriteForAge(spriteProvider);
        this.collidesWithWorld = collidesWithWorld;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }
}

