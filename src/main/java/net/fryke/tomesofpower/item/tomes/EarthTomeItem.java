package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public class EarthTomeItem extends TomeItem {
    public EarthTomeItem(Settings settings) {
        super(settings);
        tomeId = TomeIdentifiers.EARTH_TOME;

        // this is the default spell
        selectedSpell = SpellIdentifiers.DIG_SPELL_ID;

        // add the spells we want
        spellList.add(SpellIdentifiers.DIG_SPELL_ID);
        spellList.add(SpellIdentifiers.PLOW_SPELL_ID);
        spellList.add(SpellIdentifiers.PICK_UP_SPELL_ID);
    }
}
