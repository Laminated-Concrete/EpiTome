package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestingTomeItem extends TomeItem {
    public TestingTomeItem(Settings settings) {
        super(settings);
        tomeId = TomeIdentifiers.TESTING_TOME;

        // this is the default spell
        selectedSpell = SpellIdentifiers.EMBER_SPELL_ID;

        // add the spells we want
        spellList.add(SpellIdentifiers.DIG_SPELL_ID);
        spellList.add(SpellIdentifiers.PLOW_SPELL_ID);
        spellList.add(SpellIdentifiers.PICK_UP_SPELL_ID);

        spellList.add(SpellIdentifiers.EMBER_SPELL_ID);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }
}
