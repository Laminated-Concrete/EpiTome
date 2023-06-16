package net.fryke.epitome.spells.types;

import net.fryke.epitome.spells.SpellIdentifiers;

public abstract class SelfSpell extends Spell {
    public SelfSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.SELF_SPELL_TYPE;
    }
}
