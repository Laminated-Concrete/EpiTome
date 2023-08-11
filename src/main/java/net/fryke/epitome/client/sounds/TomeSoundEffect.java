package net.fryke.epitome.client.sounds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class TomeSoundEffect extends EntityTrackingSoundInstance {
    private Entity originEntity;
    private float range = 16;
    private float maxVolume;

    public TomeSoundEffect(Entity originEntity, SoundEvent soundEvent, boolean repeat, float initialVolume) {
        super(soundEvent, SoundCategory.NEUTRAL, initialVolume, 1.0f, originEntity, SoundInstance.createRandom().nextLong());
        this.originEntity = originEntity;
        this.volume = initialVolume;
        this.maxVolume = initialVolume;
        this.relative = true; // do we want this?
        this.repeat = repeat;
        this.repeatDelay = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.originEntity == MinecraftClient.getInstance().player) {
            this.volume = this.maxVolume;
        } else {
            // first we calc the distance and normalize it to within the max range
            float distance = (float)this.originEntity.getPos().squaredDistanceTo(MinecraftClient.getInstance().player.getPos());
            float rangeSquared = range * range;
            float normalizedDistance = (MathHelper.clamp(distance, 0.0f, rangeSquared)) / rangeSquared;

            // then we use that 0-1 value to lerp the volume
            this.volume = MathHelper.lerp(normalizedDistance, this.maxVolume, 0.0f);
        }
    }
}
