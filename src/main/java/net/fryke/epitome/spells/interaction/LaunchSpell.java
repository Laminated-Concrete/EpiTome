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

public class LaunchSpell extends InteractionSpell {

    public LaunchSpell() {
        super();
        SPELL_ID = SpellIdentifiers.LAUNCH_SPELL_ID;
        cooldownLengthTicks = 200; // 10s
        spellRange = 10; // in blocks
        chargeTimeTicks = 20; // 1s
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
            double spellStrength = 1.3;
            hitResult.getEntity().addVelocity(0, spellStrength, 0);
            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
