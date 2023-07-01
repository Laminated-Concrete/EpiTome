package net.fryke.epitome;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final Identifier STARTED_CASTING_ACCENT_ID = new Identifier(EpiTomeMod.MOD_ID, "started_casting_accent");
    public static SoundEvent STARTED_CASTING_ACCENT_EVENT = SoundEvent.of(STARTED_CASTING_ACCENT_ID);

    public static final Identifier CASTING_LOOP_ID = new Identifier(EpiTomeMod.MOD_ID, "casting_loop");
    public static SoundEvent CASTING_LOOP_EVENT = SoundEvent.of(CASTING_LOOP_ID);

    public static final Identifier FINISHED_CASTING_ACCENT_1_ID = new Identifier(EpiTomeMod.MOD_ID, "finished_casting_accent_1");
    public static SoundEvent FINISHED_CASTING_ACCENT_1_EVENT = SoundEvent.of(FINISHED_CASTING_ACCENT_1_ID);

    public static final Identifier FINISHED_CASTING_ACCENT_2_ID = new Identifier(EpiTomeMod.MOD_ID, "finished_casting_accent_2");
    public static SoundEvent FINISHED_CASTING_ACCENT_2_EVENT = SoundEvent.of(FINISHED_CASTING_ACCENT_2_ID);

    public static void registerModSoundEvents() {
        Registry.register(Registries.SOUND_EVENT, STARTED_CASTING_ACCENT_ID, STARTED_CASTING_ACCENT_EVENT);
        Registry.register(Registries.SOUND_EVENT, CASTING_LOOP_ID, CASTING_LOOP_EVENT);
        Registry.register(Registries.SOUND_EVENT, FINISHED_CASTING_ACCENT_1_ID, FINISHED_CASTING_ACCENT_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, FINISHED_CASTING_ACCENT_2_ID, FINISHED_CASTING_ACCENT_2_EVENT);
    }
}
