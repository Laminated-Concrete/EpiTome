package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Spell {
    public Identifier SPELL_TYPE;
    public Identifier SPELL_ID;
    public int cooldownLengthTicks = 0;

    public Spell() {

    }

    public Identifier getSpellType() {
        return SPELL_TYPE;
    }

    public Identifier getSpellId() {
        return SPELL_ID;
    }

    public abstract void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome);
}
