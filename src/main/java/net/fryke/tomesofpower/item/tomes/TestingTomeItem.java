package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestingTomeItem extends TomeItem {

    public TestingTomeItem(Settings settings) {
        super(settings);
        spellList.add(SpellIdentifiers.EMBER_SPELL_ID);
        selectedSpell = SpellIdentifiers.EMBER_SPELL_ID; // this is the default spell
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

//        TomeEntity bookEntity = new TomeEntity(ModEntities.TOME_ENTITY_ENTITY_TYPE, world, user);
//        bookEntity.setPosition(user.getX(), user.getY(), user.getZ());
//        world.spawnEntity(bookEntity);

        return super.use(world, user, hand);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchantingTableBlockEntity(pos, state);
    }
}
