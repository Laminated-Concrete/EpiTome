package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public abstract class ProjectileSpell extends Spell {
    public ProjectileSpell() {
        super();
        SPELL_TYPE = SpellIdentifiers.PROJECTILE_ENTITY_SPELL_TYPE;
    }
}
