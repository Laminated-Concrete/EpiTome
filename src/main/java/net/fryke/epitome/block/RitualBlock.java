package net.fryke.epitome.block;

import net.fryke.epitome.entity.RitualBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RitualBlock extends BlockWithEntity implements BlockEntityProvider {
    public RitualBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Optional optional = world.getBlockEntity(pos, ModBlocks.RITUAL_BLOCK_ENTITY);
        if(optional.isPresent()) {
            RitualBlockEntity blockEntity = (RitualBlockEntity) optional.get();
            ActionResult result = blockEntity.onUse(state, world, pos, player, hand, hit);
            return result;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.RITUAL_BLOCK_ENTITY, (world1, pos, state1, be) -> {
            ((RitualBlockEntity) world.getBlockEntity(pos)).tick(world1, pos, state1, be);
        });
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RitualBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }
}
