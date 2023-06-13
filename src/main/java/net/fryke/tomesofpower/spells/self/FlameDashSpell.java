package net.fryke.tomesofpower.spells.self;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryke.tomesofpower.effects.ModEffects;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.SelfSpell;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlameDashSpell extends SelfSpell {
    public FlameDashSpell() {
        super();
        SPELL_ID = SpellIdentifiers.FLAME_DASH_SPELL_ID;
        cooldownLengthTicks = 100; // 5s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        double dashStrength = 2;

        // Get the player's rotation pitch and yaw angles
        float pitch = caster.getPitch();
        float yaw = caster.getYaw();

        // Convert the pitch and yaw angles to radians
        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);

        // Calculate the direction vector
        double x = -MathHelper.sin((float)yawRadians) * MathHelper.cos((float)pitchRadians);
        double y = -MathHelper.sin((float)pitchRadians);
        double z = MathHelper.cos((float)yawRadians) * MathHelper.cos((float)pitchRadians);
        Vec3d direction = new Vec3d(x, y, z).normalize();

        caster.addVelocity(direction.multiply(dashStrength));
        caster.velocityModified = true;

        StatusEffectInstance effectInstance = new StatusEffectInstance(ModEffects.FIRE_DASH_EFFECT_TYPE, 10, 0); // duration is in ticks
        caster.addStatusEffect(effectInstance);

        caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
