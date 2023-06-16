package net.fryke.epitome.spells.types;

import net.fryke.epitome.spells.SpellIdentifiers;

public abstract class InteractionSpell extends Spell {
    public InteractionSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.INTERACTION_SPELL_TYPE;
    }
}
