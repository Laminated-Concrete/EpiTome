package net.fryke.tomesofpower.rituals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Arrays;

public class RitualHelpers {
    public static Identifier determineRitual(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return RitualIdentifiers.KNOWLEDGE_RITUAL_ID;
    }

    public static Boolean isBlockType(World world, BlockPos pos, Block blockType) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() == blockType;
    }

    public static Boolean checkStructure(World world, BlockPos ritualBlockPos, Identifier ritualStructureId) {
        RitualStructure ritualStructure = RitualStructures.getRitualStructure(ritualStructureId);

        Boolean validStructure = true; // we start assuming we are good to go
        for (BlockEntry blockEntry : ritualStructure.blockEntries()) {
            Vec3i offset = blockEntry.blockPosOffset();
            Block[] allowedBlocks = blockEntry.allowedBlockTypes();
            Block[] disallowedBlocks = blockEntry.disallowedBlockTypes();

            BlockPos targetPos = ritualBlockPos.add(offset);
            Block targetBlock = world.getBlockState(targetPos).getBlock();

            if(allowedBlocks.length != 0) {
                // if we have specifically allowed blocks, check them
                if(!Arrays.stream(allowedBlocks).anyMatch((block) -> block == targetBlock)) {
                    // if the current block does not match any of our allowed blocks, we break and set false because we must have an allowed block
                    validStructure = false;
                    break;
                }
            } else if(disallowedBlocks.length != 0) {
                // otherwise we aren't looking for specific blocks, so we just make sure the one there isn't disallowed
                if(Arrays.stream(disallowedBlocks).anyMatch((block) -> block == targetBlock)) {
                    // the target block matches one of our disallowed blocks, so we break and set false
                    validStructure = false;
                    break;
                }
            }
        }

        return validStructure;
    }
}
