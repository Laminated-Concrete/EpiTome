package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public class AirTomeItem extends TomeItem {
    public AirTomeItem(Settings settings) {
        super(settings);
        tomeId = TomeIdentifiers.AIR_TOME;

        // this is the default spell
        selectedSpell = SpellIdentifiers.LAUNCH_SPELL_ID;

        // add the spells we want
        spellList.add(SpellIdentifiers.LAUNCH_SPELL_ID);
        spellList.add(SpellIdentifiers.PUSH_SPELL_ID);
        spellList.add(SpellIdentifiers.PULL_SPELL_ID);
    }
}
