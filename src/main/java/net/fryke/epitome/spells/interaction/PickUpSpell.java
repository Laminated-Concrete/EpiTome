package net.fryke.epitome.spells.interaction;

import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.types.Spell;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PickUpSpell extends Spell {
    private BlockState storedBlockState;
    private NbtCompound storedNbtData = new NbtCompound();

    public PickUpSpell() {
        super();
        cooldownLengthTicks = 10; // 0.5s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
        if(storedBlockState == null) {
            // if we don't have a stored block state then we want to grab the block
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos targetBlockPos = blockHitResult.getBlockPos();

                // we always save the block state
                storedBlockState = world.getBlockState(targetBlockPos);

                // blocks with things like inventories use NBT to store that info
                BlockEntity blockEntity = world.getBlockEntity(targetBlockPos);
                if(blockEntity != null) {
                    storedNbtData = blockEntity.createNbt();
                }

                // We remove the block entity first to prevent inventories from spilling out
                world.removeBlockEntity(targetBlockPos);
                world.removeBlock(targetBlockPos, false);

                caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
            }
        } else {
            if (hitResult instanceof BlockHitResult blockHitResult) {

                // we need to calc the target coordinate based on where they right-clicked on the block
                BlockPos hitBlockPos = blockHitResult.getBlockPos();
                Direction direction = blockHitResult.getSide();
                BlockPos targetBlockPos = hitBlockPos.offset(direction);

                if(storedBlockState.contains(Properties.FACING)) {
                    Direction blockFacingDirection = Direction.fromRotation(caster.getYaw()).getOpposite();
                    storedBlockState = storedBlockState.with(Properties.FACING, blockFacingDirection);
                }

                if(storedBlockState.contains(Properties.HORIZONTAL_FACING)) {
                    Direction blockFacingDirection = Direction.fromRotation(caster.getYaw()).getOpposite();
                    storedBlockState = storedBlockState.with(Properties.HORIZONTAL_FACING, blockFacingDirection);
                }

                if(storedBlockState.contains(Properties.CHEST_TYPE)) {
                    // TODO set this up so chests will auto-attach to other chests?
//                    ItemPlacementContext context = new ItemPlacementContext(world, caster, hand, null, blockHitResult);
                    storedBlockState = storedBlockState.with(Properties.CHEST_TYPE, ChestType.SINGLE);
                }

                // we always want to set the block state. this is how we place the block in the world
                world.setBlockState(targetBlockPos, storedBlockState);

                // holy shit this works
                if(storedNbtData != null) {
                    BlockEntity blockEntity = world.getBlockEntity(targetBlockPos);
                    if(blockEntity != null) {
                        blockEntity.readNbt(storedNbtData);
                    }
                }

                storedBlockState = null;
                storedNbtData = new NbtCompound();
                caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
            }
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
