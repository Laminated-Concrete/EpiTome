package net.fryke.epitome.item.tomes;

import net.fryke.epitome.spells.SpellIdentifiers;

public class FireTomeItem extends TomeItem {
    public FireTomeItem(Settings settings) {
        super(settings);
        tomeId = TomeIdentifiers.FIRE_TOME;

        // this is the default spell
        selectedSpell = SpellIdentifiers.EMBER_SPELL_ID;

        // add the spells we want
        spellList.add(SpellIdentifiers.EMBER_SPELL_ID);
        spellList.add(SpellIdentifiers.BURNING_GROUND_SPELL_ID);
        spellList.add(SpellIdentifiers.FLAME_DASH_SPELL_ID);

        // this sets the default on-load page texture for the book model
        spellPageAnimatable.setSpellTextureName(SpellIdentifiers.EMBER_SPELL_ID.getPath());
    }
}
