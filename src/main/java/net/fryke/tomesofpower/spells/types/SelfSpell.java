package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public abstract class SelfSpell extends Spell {
    public SelfSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.SELF_SPELL_TYPE;
    }
}
