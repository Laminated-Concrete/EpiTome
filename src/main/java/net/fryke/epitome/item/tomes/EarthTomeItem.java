package net.fryke.epitome.item.tomes;

import net.fryke.epitome.spells.SpellIdentifiers;

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

        // this sets the default on-load page texture for the book model
        spellPageAnimatable.setSpellTextureName(SpellIdentifiers.DIG_SPELL_ID.getPath());
    }
}
