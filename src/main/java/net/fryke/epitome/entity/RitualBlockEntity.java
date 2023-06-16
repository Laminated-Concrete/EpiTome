package net.fryke.epitome.entity;

import net.fryke.epitome.block.ModBlocks;
import net.fryke.epitome.block.RitualBlock;
import net.fryke.epitome.rituals.types.Ritual;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualBlockEntity extends BlockEntity {
    public RitualBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RITUAL_BLOCK_ENTITY, pos, state);
    }

    public RitualBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RitualBlockEntity be) {
        Block block = state.getBlock();
        if(block instanceof RitualBlock) {
            Ritual ritual = ((RitualBlock) block).getCurrentRitual();
            if(ritual != null) {
                ritual.tick();
            }
        }
    }
}
