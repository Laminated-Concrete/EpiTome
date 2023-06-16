package net.fryke.epitome.effects;

import net.fryke.epitome.EpiTomeMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect FIRE_DASH_EFFECT_TYPE = registerEffect(
            new Identifier(EpiTomeMod.MOD_ID, "flame_dash_effect"),
            new FlameDashEffect());

    public static final StatusEffect FEATHER_FALL_EFFECT_TYPE = registerEffect(
            new Identifier(EpiTomeMod.MOD_ID, "feather_fall_effect"),
            new FeatherFallEffect());

    public static void registerModEffects() {
        EpiTomeMod.LOGGER.info("Registering Mod Effects for " + EpiTomeMod.MOD_ID);
    }

    public static StatusEffect registerEffect(Identifier effectIdentifier, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, effectIdentifier, effect);
    }
}
