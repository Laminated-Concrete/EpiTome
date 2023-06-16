package net.fryke.epitome.spells.types;

import net.fryke.epitome.spells.SpellIdentifiers;

public abstract class CustomEntitySpell extends Spell {
    public CustomEntitySpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.CUSTOM_ENTITY_SPELL_TYPE;
    }
}
