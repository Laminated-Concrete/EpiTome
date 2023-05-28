package net.fryke.tomesofpower.spells.interaction;

import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PickUpSpell extends Spell {
    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        // TODO how on earth are we gunna do this?
        // something like
        // 1. get the block they are looking at
        // 2. store it in a variable here?
        // 3. remove the block from the world
        // 4. flip states to 'storing a block'
        // 5. next interaction take the block stored and place it again?
        caster.getItemCooldownManager().set(tome, 10); // number is in ticks
    }
}
