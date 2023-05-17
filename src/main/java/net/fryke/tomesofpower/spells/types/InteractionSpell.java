package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.ToPMod;
import net.minecraft.util.Identifier;

public class InteractionSpell implements SpellInterface {
    public static final Identifier INTERACTION_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "interaction_spell_type");

    @Override
    public Identifier getSpellType() {
        return INTERACTION_SPELL_TYPE;
    }
}
