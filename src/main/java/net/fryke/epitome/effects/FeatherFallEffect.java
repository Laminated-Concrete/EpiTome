package net.fryke.epitome.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FeatherFallEffect extends StatusEffect {
    public FeatherFallEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF4500); // Choose a custom color for the effect
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // so how do we negate fall damage?

        entity.fallDistance = 0f;
    }
}
