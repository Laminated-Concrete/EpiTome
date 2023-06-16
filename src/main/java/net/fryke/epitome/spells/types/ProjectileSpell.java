package net.fryke.epitome.spells.types;

import net.fryke.epitome.spells.SpellIdentifiers;

public abstract class ProjectileSpell extends Spell {
    public ProjectileSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.PROJECTILE_ENTITY_SPELL_TYPE;
    }
}
