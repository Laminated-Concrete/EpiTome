package net.fryke.epitome.block;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.entity.RitualBlockEntity;
import net.fryke.epitome.rituals.ModRituals;
import net.fryke.epitome.rituals.types.Ritual;
import net.fryke.epitome.rituals.RitualHelpers;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class RitualBlock extends BlockWithEntity implements BlockEntityProvider {
    private Ritual currentRitual;
    private ItemStack resultItemStack;

    public RitualBlock(Settings settings) {
        super(settings);
    }

    public Ritual getCurrentRitual() { return currentRitual; }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            if(resultItemStack != null) {
                EpiTomeMod.LOGGER.info("We have a tome item stack");
                // we have a tome in the ritual block, so we let the user take it
                Boolean succeeded = player.giveItemStack(resultItemStack);
                if(succeeded) {
                    EpiTomeMod.LOGGER.info("setting tome item stack to null");
                    resultItemStack = null;
                    return ActionResult.SUCCESS;
                }
            } else {
                EpiTomeMod.LOGGER.info("no tome item stack");
                if(this.currentRitual != null) {
                    if(this.currentRitual.state == Ritual.RitualStates.RUNNING) {
                        EpiTomeMod.LOGGER.info("There is a ritual in progress, do nothing");
                        return ActionResult.CONSUME;
                    }
                } else {
                    EpiTomeMod.LOGGER.info("trying to start ritual");
                    Identifier ritualId = RitualHelpers.determineRitual(state, world, pos, player, hand, hit);
                    Class ritualClass = ModRituals.ritualRegistry.get(ritualId).getClass();
                    try {
                        Ritual ritual = (Ritual) ritualClass.getDeclaredConstructor().newInstance();

                        ritual.setData(player, state, pos, world);
                        if (ritual.canStart()) {
                            EpiTomeMod.LOGGER.info("good to go, trying to start ritual");
                            this.currentRitual = ritual;
                            // TODO play a sound effect? or do we do that in the ritual for ritual-specific effects?
                            ritual.startRitual();
                            return ActionResult.SUCCESS;
                        } else {
                            EpiTomeMod.LOGGER.info("ritual cannot start");
                            return ActionResult.FAIL;
                            // TODO play a 'failed' sound effect + particles. smoke maybe?
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |NoSuchMethodException e) {
                        e.printStackTrace();
                        return ActionResult.FAIL;
                    }
                }
            }
        } else {
            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }

    public void insertTome(Identifier tomeId) {
        EpiTomeMod.LOGGER.info("inserting a tome into the ritual block");
        this.resultItemStack = new ItemStack(Registries.ITEM.get(tomeId));
        this.currentRitual = null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.RITUAL_BLOCK_ENTITY, (world1, pos, state1, be) -> RitualBlockEntity.tick(world1, pos, state1, be));
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
