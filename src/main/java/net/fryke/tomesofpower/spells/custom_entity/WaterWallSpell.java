package net.fryke.tomesofpower.spells.custom_entity;

import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.CustomEntitySpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WaterWallSpell extends CustomEntitySpell {
    public WaterWallSpell() {
        SPELL_ID = SpellIdentifiers.WATER_WALL_SPELL_ID;
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
