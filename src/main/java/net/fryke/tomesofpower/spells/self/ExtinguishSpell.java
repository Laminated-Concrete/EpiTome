package net.fryke.tomesofpower.spells.self;

import net.fryke.tomesofpower.effects.ModEffects;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.SelfSpell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExtinguishSpell extends SelfSpell {
    public ExtinguishSpell() {
        SPELL_ID = SpellIdentifiers.EXTINGUISH_SPELL_ID;
        cooldownLengthTicks = 200; // 10s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        StatusEffectInstance effectInstance = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 0); // 5s duration
        caster.addStatusEffect(effectInstance);

        caster.extinguish();

        caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
    }
}
