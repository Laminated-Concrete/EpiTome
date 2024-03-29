package net.fryke.epitome.spells.interaction;

import net.fryke.epitome.EpiTomeUtilities;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.InteractionSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PushSpell extends InteractionSpell {

    public PushSpell() {
        super();
        SPELL_ID = SpellIdentifiers.PUSH_SPELL_ID;
        cooldownLengthTicks = 100; // 5s
        spellRange = 10; // in blocks
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        Vec3d eyePos = caster.getCameraPosVec(1.0f);
        Vec3d rotationVec = caster.getRotationVec(1.0f);
        Vec3d maxPos = eyePos.add(rotationVec.x * spellRange, rotationVec.y * spellRange, rotationVec.z * spellRange);

        Box box = caster.getBoundingBox().stretch(rotationVec.multiply(spellRange)).expand(1.0, 1.0, 1.0);

        // odd problem here. using the raycast directly seems to work, but using the helper method I put together doesn't. strange
        EntityHitResult hitResult = EpiTomeUtilities.configurableRaycast(caster, eyePos, maxPos, box, entity -> !entity.isSpectator(), spellRange*spellRange, 0.3f);

        if(hitResult != null) {
            Vec3d targetPos = hitResult.getEntity().getPos();
            Vec3d direction = targetPos.subtract(eyePos).normalize();

            // This part handles the situation where the target entity is on the ground, and so the Y direction actually stops it from getting pushed
            // in this case, we set the Y component of the direction to a flat +0.3
            if(hitResult.getEntity().isOnGround() && caster.getY() > direction.getY() + 3) {
                direction = new Vec3d(direction.getX(), 0.4, direction.getZ());
            }

            double spellStrength = 1.5;
            hitResult.getEntity().addVelocity(direction.x * spellStrength, direction.y * spellStrength, direction.z * spellStrength);
            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        Vec3d eyePos = caster.getCameraPosVec(1.0f);
        Vec3d rotationVec = caster.getRotationVec(1.0f);
        ParticleEffect effect = ParticleTypes.EFFECT;
        world.addParticle(effect, eyePos.getX(), eyePos.getY(), eyePos.getZ(), (rotationVec.x * 2) + Math.random() * 0.5, (rotationVec.y * 2) + Math.random() * 0.5, (rotationVec.z * 2) + Math.random() * 0.5);
        world.addParticle(effect, eyePos.getX(), eyePos.getY(), eyePos.getZ(), (rotationVec.x * 2) + Math.random() * 0.5, (rotationVec.y * 2) + Math.random() * 0.5, (rotationVec.z * 2) + Math.random() * 0.5);
        world.addParticle(effect, eyePos.getX(), eyePos.getY(), eyePos.getZ(), (rotationVec.x * 2) + Math.random() * 0.5, (rotationVec.y * 2) + Math.random() * 0.5, (rotationVec.z * 2) + Math.random() * 0.5);
        world.addParticle(effect, eyePos.getX(), eyePos.getY(), eyePos.getZ(), (rotationVec.x * 2) + Math.random() * 0.5, (rotationVec.y * 2) + Math.random() * 0.5, (rotationVec.z * 2) + Math.random() * 0.5);
        world.addParticle(effect, eyePos.getX(), eyePos.getY(), eyePos.getZ(), (rotationVec.x * 2) + Math.random() * 0.5, (rotationVec.y * 2) + Math.random() * 0.5, (rotationVec.z * 2) + Math.random() * 0.5);
    }
}
