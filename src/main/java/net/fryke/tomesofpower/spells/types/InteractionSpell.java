package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public abstract class InteractionSpell extends Spell {
    public InteractionSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.INTERACTION_SPELL_TYPE;
    }
}
