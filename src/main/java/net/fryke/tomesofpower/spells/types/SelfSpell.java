package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.ToPMod;
import net.minecraft.util.Identifier;

public class SelfSpell implements SpellInterface {
    public static final Identifier SELF_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "self_spell_type");

    @Override
    public Identifier getSpellType() {
        return SELF_SPELL_TYPE;
    }
}
