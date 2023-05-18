package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.ToPMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Spell {
    public Identifier SPELL_TYPE;
    public Identifier SPELL_ID;

    public Spell() {

    }

    public Identifier getSpellType() {
        return SPELL_TYPE;
    }

    public abstract void castSpell(World world, PlayerEntity caster, Hand hand);
}
