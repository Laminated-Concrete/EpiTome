package net.fryke.epitome.spells.self;

import net.fryke.epitome.effects.ModEffects;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.SelfSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class WaterJumpSpell extends SelfSpell {
    public WaterJumpSpell() {
        SPELL_ID = SpellIdentifiers.WATER_JUMP_SPELL_ID;
        cooldownLengthTicks = 200; // 10s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        double jumpStrength = 2;
        double statusEffectRadius = 2;

        // here we want to slow all enties around the player
        Box box = new Box(
                caster.getX() - statusEffectRadius, caster.getY() - statusEffectRadius, caster.getZ() - statusEffectRadius,
                caster.getX() + statusEffectRadius, caster.getY() + statusEffectRadius, caster.getZ() + statusEffectRadius);
        List<Entity> entities = world.getOtherEntities(caster, box, (entity) -> !entity.isSpectator());

        entities.forEach((entity) -> {
            if(entity instanceof LivingEntity) {
                StatusEffectInstance effectInstance = new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2); // duration is in ticks
                ((LivingEntity) entity).addStatusEffect(effectInstance);
            }
        });

        // then we want to add some feather falling to them
        StatusEffectInstance effectInstance = new StatusEffectInstance(ModEffects.FEATHER_FALL_EFFECT_TYPE, 100, 0); // duration is in ticks
        caster.addStatusEffect(effectInstance);

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
        Vec3d rawDirection = new Vec3d(x, y, z).normalize();

        // we want to limit how high the jump will send the player, so we lock it to 0.5
        Vec3d direction = new Vec3d(-rawDirection.getX(), 0.5, -rawDirection.getZ());

        caster.addVelocity(direction.multiply(jumpStrength));
        caster.velocityModified = true;

        caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
