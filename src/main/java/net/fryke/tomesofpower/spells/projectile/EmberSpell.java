package net.fryke.tomesofpower.spells.projectile;

import net.fryke.tomesofpower.entity.ModEntities;
import net.fryke.tomesofpower.entity.spell.EmberSpellEntity;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.ProjectileSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EmberSpell extends ProjectileSpell {
    public EmberSpell() {
        super();
        SPELL_ID = SpellIdentifiers.EMBER_SPELL_ID;
        chargeTimeTicks = 10; // 0.5s
        lifetimeTicks = 40; // 2s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        float yawOffset = 8f;
        float speed = 1.4f;
        float divergence = 2f;
        float gravity = 0.03f;
        float dragScalar = 0.95f;

        // TODO can we like space these out so they fire unevenly? maybe make the sound here too?
        world.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

        // For this spell, we want to shoot out three projectiles in a fan
        EmberSpellEntity spellEntityLeft = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster, this);
        spellEntityLeft.setVelocity(caster, caster.getPitch(), caster.getYaw() + yawOffset, 0.0F, speed, divergence);
        spellEntityLeft.setLifetimeTicks(lifetimeTicks);
        world.spawnEntity(spellEntityLeft);

        EmberSpellEntity spellEntityMiddle = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster, this);
        spellEntityMiddle.setVelocity(caster, caster.getPitch(), caster.getYaw(), 0.0F, speed, divergence);
        spellEntityMiddle.setLifetimeTicks(lifetimeTicks);
        world.spawnEntity(spellEntityMiddle);

        EmberSpellEntity spellEntityRight = new EmberSpellEntity(ModEntities.EMBER_SPELL_ENTITY_TYPE, world, gravity, dragScalar, caster, this);
        spellEntityRight.setVelocity(caster, caster.getPitch(), caster.getYaw() - yawOffset, 0.0F, speed, divergence);
        spellEntityRight.setLifetimeTicks(lifetimeTicks);
        world.spawnEntity(spellEntityRight);

        caster.getItemCooldownManager().set(tome, 20); // number is in ticks
    }
}
