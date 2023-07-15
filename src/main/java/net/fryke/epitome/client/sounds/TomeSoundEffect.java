package net.fryke.epitome.client.sounds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TomeSoundEffect extends MovingSoundInstance {
    private PlayerEntity caster;
    private Vec3d pos;
    private float range = 10;

    public TomeSoundEffect(PlayerEntity caster, SoundEvent soundEvent, boolean repeat) {
        super(soundEvent, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.caster = caster;
        this.repeat = repeat;
        this.repeatDelay = 0;
        this.volume = 0.7f;
        this.relative = true; // do we want this?
        if(caster != null) {
            this.pos = caster.getPos();
        }
    }

    @Override
    public void tick() {
//        if(this.caster.isRemoved()) {
//            this.setDone();
//            return;
//        }
//
//        if(this.caster == MinecraftClient.getInstance().player) {
//            this.volume = 0.7f;
//        } else {
//            // we get updated coordinates of the player entity
//            this.pos = this.caster.getPos();
//            this.y = (float)this.caster.getY();
//            this.z = (float)this.caster.getZ();
//
//            // first we calc the distance and normalize it to within the max range
//            float distance = (float)this.pos.squaredDistanceTo(MinecraftClient.getInstance().player.getPos());
//            float normalizedDistance = (MathHelper.clamp(distance, 0.0f, range)) / range;
//
//            // then we use that 0-1 value to lerp the volume
//            this.volume = MathHelper.lerp(normalizedDistance, 0.0f, 0.7f);
//        }
    }
}
