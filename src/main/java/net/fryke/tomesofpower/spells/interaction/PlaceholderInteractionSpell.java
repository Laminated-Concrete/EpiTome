package net.fryke.tomesofpower.spells.interaction;

import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.types.InteractionSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PlaceholderInteractionSpell extends InteractionSpell {
    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        caster.getItemCooldownManager().set(tome, 20); // number is in ticks
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
