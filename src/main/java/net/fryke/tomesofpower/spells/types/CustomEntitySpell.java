package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CustomEntitySpell extends Spell {
    public CustomEntitySpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.CUSTOM_ENTITY_SPELL_TYPE;
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand) {

    }
}
