package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.spells.SpellIdentifiers;

public class WaterTomeItem extends TomeItem {
    public WaterTomeItem(Settings settings) {
        super(settings);
        tomeId = TomeIdentifiers.WATER_TOME;

        // this is the default spell
        selectedSpell = SpellIdentifiers.EXTINGUISH_SPELL_ID;

        // add the spells we want
        spellList.add(SpellIdentifiers.WATER_JUMP_SPELL_ID);
        spellList.add(SpellIdentifiers.WATER_WALL_SPELL_ID);
        spellList.add(SpellIdentifiers.EXTINGUISH_SPELL_ID);
    }
}
