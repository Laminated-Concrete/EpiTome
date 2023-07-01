package net.fryke.epitome.client.sounds;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class SpellSoundEffect extends MovingSoundInstance {
    public SpellSoundEffect(SoundEvent soundEvent) {
        super(soundEvent, SoundCategory.PLAYERS, SoundInstance.createRandom());
//        this.player = player;
        this.repeat = false; // I don't think spell sound effects will ever repeat
//        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = true;
    }

    @Override
    public void tick() {

    }
}
