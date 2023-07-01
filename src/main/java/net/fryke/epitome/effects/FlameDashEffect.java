package net.fryke.epitome.effects;

import net.fryke.epitome.helpers.ModLogger;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlameDashEffect extends StatusEffect {
    private static final int FIRE_DURATION_TICKS = 40; // 2s
    private static final double EFFECT_RANGE = 1.0; // Range of the dash effect

    public FlameDashEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xFF4500); // Choose a custom color for the effect
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        ModLogger.log("trying to do the thing");

        // Define the corners of the box we'll use for searching for entities to set on fire
        Vec3d pos1 = new Vec3d(entity.getX() - EFFECT_RANGE, entity.getY() - EFFECT_RANGE, entity.getZ() - EFFECT_RANGE);
        Vec3d pos2 = new Vec3d(entity.getX() + EFFECT_RANGE, entity.getY() + EFFECT_RANGE, entity.getZ() + EFFECT_RANGE);

        // Get the entities within the effect range and set them on fire if they aren't on fire already
        World world = entity.getWorld();
        world.getEntitiesByClass(LivingEntity.class, new Box(pos1, pos2), (livingEntity) -> !livingEntity.equals(entity))
            .forEach(livingEntity -> {
                if (!livingEntity.isOnFire()) {
                    livingEntity.setOnFireFor(FIRE_DURATION_TICKS);
                }
            });
    }
}