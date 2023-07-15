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

    public static final Identifier READY_TO_CAST_ACCENT_ID = new Identifier(EpiTomeMod.MOD_ID, "ready_to_cast_accent");
    public static SoundEvent READY_TO_CAST_ACCENT_EVENT = SoundEvent.of(READY_TO_CAST_ACCENT_ID);

    public static final Identifier FINISHED_CASTING_ACCENT_ID = new Identifier(EpiTomeMod.MOD_ID, "finished_casting_accent");
    public static SoundEvent FINISHED_CASTING_ACCENT_EVENT = SoundEvent.of(FINISHED_CASTING_ACCENT_ID);

    public static void registerModSoundEvents() {
        Registry.register(Registries.SOUND_EVENT, STARTED_CASTING_ACCENT_ID, STARTED_CASTING_ACCENT_EVENT);
        Registry.register(Registries.SOUND_EVENT, CASTING_LOOP_ID, CASTING_LOOP_EVENT);
        Registry.register(Registries.SOUND_EVENT, FINISHED_CASTING_ACCENT_ID, FINISHED_CASTING_ACCENT_EVENT);
        Registry.register(Registries.SOUND_EVENT, READY_TO_CAST_ACCENT_ID, READY_TO_CAST_ACCENT_EVENT);
    }
}
