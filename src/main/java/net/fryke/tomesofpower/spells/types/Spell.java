package net.fryke.tomesofpower.spells.types;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Spell {
    public Identifier SPELL_TYPE;
    public Identifier SPELL_ID;
    public int cooldownLengthTicks = 0;
    public int chargeTimeTicks = 0;
    public int lifetimeTicks = 0;
    public double spellRange = 0;

    public Spell() {

    }

    public Identifier getSpellType() {
        return SPELL_TYPE;
    }

    public Identifier getSpellId() {
        return SPELL_ID;
    }

    public abstract void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome);
    public abstract void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome);
}
