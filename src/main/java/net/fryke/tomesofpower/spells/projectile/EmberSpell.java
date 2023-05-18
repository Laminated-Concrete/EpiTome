package net.fryke.tomesofpower.spells.projectile;

import net.fryke.tomesofpower.entity.ModEntities;
import net.fryke.tomesofpower.entity.custom.TestingProjectileEntity;
import net.fryke.tomesofpower.entity.spell.EmberSpellEntity;
import net.fryke.tomesofpower.spells.types.ProjectileSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EmberSpell extends ProjectileSpell {
    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand) {
        float yawOffset = 8f;
        float speed = 1.4f;
        float divergence = 2f;
        float gravity = 0.03f;
        float dragScalar = 0.95f;

        // TODO can we like space these out so they fire unevenly? maybe make the sound here too?
        // For this spell, we want to shoot out three projectiles in a fan
        EmberSpellEntity spellEntityLeft = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster);
        spellEntityLeft.setVelocity(caster, caster.getPitch(), caster.getYaw() + yawOffset, 0.0F, speed, divergence);
        world.spawnEntity(spellEntityLeft);

        EmberSpellEntity spellEntityMiddle = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster);
        spellEntityMiddle.setVelocity(caster, caster.getPitch(), caster.getYaw(), 0.0F, speed, divergence);
        world.spawnEntity(spellEntityMiddle);

        EmberSpellEntity spellEntityRight = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster);
        spellEntityRight.setVelocity(caster, caster.getPitch(), caster.getYaw() - yawOffset, 0.0F, speed, divergence);
        world.spawnEntity(spellEntityRight);
    }
}
