package net.fryke.epitome.entity;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.block.ModBlocks;
import net.fryke.epitome.rituals.ModRituals;
import net.fryke.epitome.rituals.RitualManager;
import net.fryke.epitome.rituals.types.Ritual;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class RitualBlockEntity extends BlockEntity {
    private Ritual currentRitual;
    private ItemStack resultItemStack;

    public RitualBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RITUAL_BLOCK_ENTITY, pos, state);
    }

    public RitualBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Ritual getCurrentRitual() { return currentRitual; }

    public void tick(World world, BlockPos pos, BlockState state, RitualBlockEntity blockEntity) {
        if(currentRitual != null) {
            currentRitual.tick();
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            if(resultItemStack != null) {
                ModLogger.log("We have a tome item stack");
                // we have a tome in the ritual block, so we let the user take it
                boolean succeeded = player.giveItemStack(resultItemStack);
                if(succeeded) {
                    ModLogger.log("setting tome item stack to null");
                    resultItemStack = null;
                    return ActionResult.SUCCESS;
                }
            } else {
                ModLogger.log("no tome item stack");
                if(this.currentRitual != null) {
                    if(this.currentRitual.state == Ritual.RitualStates.RUNNING) {
                        ModLogger.log("There is a ritual in progress, do nothing");
                        return ActionResult.CONSUME;
                    }
                } else {
                    ModLogger.log("trying to start ritual");
                    Identifier ritualId = RitualManager.getInstance().determineRitual(state, world, pos, player, hand, hit);
                    if(ritualId == null) {
                        ModLogger.log("we failed to find a ritual ID");
                        // if we have failed to find a ritual ID, then we fail
                        // TODO we probably want to play a sound or notify the player of the problem?
                        return ActionResult.FAIL;
                    }

                    Class<? extends Object> ritualClass = Objects.requireNonNull(ModRituals.ritualRegistry.get(ritualId)).getClass();
                    try {
                        Ritual ritual = (Ritual) ritualClass.getDeclaredConstructor().newInstance();

                        ritual.setData(player, state, pos, world, this);
                        if (ritual.canStart()) {
                            ModLogger.log("good to go, trying to start ritual");
                            this.currentRitual = ritual;
                            // TODO play a sound effect? or do we do that in the ritual for ritual-specific effects?
                            ritual.startRitual();
                            return ActionResult.SUCCESS;
                        } else {
                            ModLogger.log("ritual cannot start");
                            return ActionResult.FAIL;
                            // TODO play a 'failed' sound effect + particles. smoke maybe?
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
        ModLogger.log("inserting a tome into the ritual block");
        this.resultItemStack = new ItemStack(Registries.ITEM.get(tomeId));
        this.currentRitual = null;
    }

    public void cancelRitual() {
        this.currentRitual = null;
    }
}
