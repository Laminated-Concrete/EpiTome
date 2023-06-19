package net.fryke.epitome.spells.interaction;

import net.fryke.epitome.EpiTomeUtilities;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.InteractionSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PullSpell extends InteractionSpell {

    public PullSpell() {
        super();
        SPELL_ID = SpellIdentifiers.PULL_SPELL_ID;
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
            Vec3d direction = eyePos.subtract(targetPos).normalize();

            // This part adds a little boost to the vertical part of the velocity when the player is on the same Y level
            // helps items reach the player and for entities to get a bit of air
            double yMod = 2;
            if(caster.getY() > targetPos.getY() + 3) {
                yMod = 1;
            }

            double spellStrength = 1.3;
            hitResult.getEntity().addVelocity(direction.x * spellStrength, direction.y * spellStrength * yMod, direction.z * spellStrength);
            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
