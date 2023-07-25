package net.fryke.epitome.entity;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.block.ModBlocks;
import net.fryke.epitome.rituals.ModRituals;
import net.fryke.epitome.rituals.RitualManager;
import net.fryke.epitome.rituals.types.Ritual;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ReceptacleBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private Ritual currentRitual;
    private int ritualTimeTicks = -1;
    private ItemStack resultItemStack;

    private enum ModelStates {
        INACTIVE,
        RUNNING,
        FINISHED,
    }
    private ModelStates animState = ModelStates.INACTIVE;
    private boolean completedRitual = false;

    public ReceptacleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RECEPTACLE_BLOCK_ENTITY, pos, state);
    }

    public ReceptacleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public Ritual getCurrentRitual() { return currentRitual; }

    public void tick(World world, BlockPos pos, BlockState state, ReceptacleBlockEntity blockEntity) {
        if(currentRitual != null) {
            currentRitual.tick();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putString("anim_state", animState.toString());
        nbt.putBoolean("completed_ritual", completedRitual);
        nbt.putInt("ritual_time_ticks", ritualTimeTicks);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        animState = ModelStates.valueOf(nbt.getString("anim_state"));
        completedRitual = nbt.getBoolean("completed_ritual");
        ritualTimeTicks = nbt.getInt("ritual_time_ticks");
        ModLogger.log("animState on read nbt = " + animState);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
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
                    completedRitual = true;
                    animState = ModelStates.INACTIVE;
                    world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                    return ActionResult.SUCCESS;
                }
            } else {
                ModLogger.log("no tome item stack");
                if(currentRitual != null) {
                    if(currentRitual.state == Ritual.RitualStates.RUNNING) {
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
                        animState = ModelStates.INACTIVE;
                        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                        return ActionResult.FAIL;
                    }

                    Class<? extends Object> ritualClass = Objects.requireNonNull(ModRituals.ritualRegistry.get(ritualId)).getClass();
                    try {
                        Ritual ritual = (Ritual) ritualClass.getDeclaredConstructor().newInstance();

                        ritual.setData(player, state, pos, world, this);
                        if (ritual.canStart()) {
                            ModLogger.log("good to go, trying to start ritual");
                            currentRitual = ritual;
                            ritualTimeTicks = currentRitual.ritualTimeLimit;
                            // TODO play a sound effect? or do we do that in the ritual for ritual-specific effects?
                            ritual.startRitual();
                            animState = ModelStates.RUNNING;
                            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                            return ActionResult.SUCCESS;
                        } else {
                            ModLogger.log("ritual cannot start");
                            animState = ModelStates.INACTIVE;
                            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
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
        resultItemStack = new ItemStack(Registries.ITEM.get(tomeId));
        currentRitual = null;
        ritualTimeTicks = -1;
        animState = ModelStates.FINISHED;
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }

    public void cancelRitual() {
        currentRitual = null;
        ritualTimeTicks = -1;
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    private <T extends GeoAnimatable> PlayState progressAnimController(AnimationState<T> tAnimationState) {
        // this controller controls the progress animation specifically
        AnimationController controller = tAnimationState.getController();

        if(animState == ModelStates.RUNNING) {
            // in the running state, we play the progress animation
            // note that we play it at specific speeds based on the length of the ritual
            // by default the animation speed is 10 seconds
            // considering that 20 ticks is about 1 second, that's 200 ticks for the full animation

            double scalar = (double)200 / (double)ritualTimeTicks;
            ModLogger.log("ritualTimeTicks = " + ritualTimeTicks + ", scalar = " + scalar);

            controller.setAnimationSpeed(scalar);
            controller.setAnimation(RawAnimation.begin().then("animation.model.progress", Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else {
            // otherwise if we are in any other state, we want to jump to the end of the animation
            // TODO
            controller.setAnimation(null);
        }

        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState spinAnimController(AnimationState<T> tAnimationState) {
        // this controller controls the progress animation specifically
        AnimationController controller = tAnimationState.getController();

        if(animState == ModelStates.RUNNING) {
            // in the running state, we spin up the ring and keep it looping
            RawAnimation anim = RawAnimation.begin().thenPlay("animation.model.spinUpRing").thenLoop("animation.model.spinRing");
            controller.setAnimation(anim);
        } else if(animState == ModelStates.FINISHED) {
            // in the finished state, we wind down the ring
            controller.setAnimation(RawAnimation.begin().then("animation.model.spinDownRing", Animation.LoopType.HOLD_ON_LAST_FRAME));
        }

        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState stateAnimController(AnimationState<T> tAnimationState) {
        AnimationController controller = tAnimationState.getController();
        if(animState == ModelStates.RUNNING) {
            controller.setAnimation(RawAnimation.begin().then("animation.model.startRitual", Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else if(animState == ModelStates.FINISHED) {
            // in the finished state, a ritual has been finished and has a book. we actually hold and do nothing for this part until the book is taken
            if(resultItemStack == null) {
//                controller.setAnimation(RawAnimation.begin().then("animation.model.endRitual", Animation.LoopType.HOLD_ON_LAST_FRAME));
            }
        } else if(animState == ModelStates.INACTIVE) {
            // In the inactive state, we don't want to play any animation at all.
            // this runs when the block is loaded in. we don't run something so that when you load into the world, nothing happens
            if(completedRitual) {
                controller.setAnimation(RawAnimation.begin().then("animation.model.endRitual", Animation.LoopType.HOLD_ON_LAST_FRAME));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<ReceptacleBlockEntity> progressController = new AnimationController<>(this, "progress_controller", 0, this::progressAnimController);
        AnimationController<ReceptacleBlockEntity> stateController = new AnimationController<>(this, "state_controller", 0, this::stateAnimController);
        AnimationController<ReceptacleBlockEntity> spinController = new AnimationController<>(this, "spin_controller", 5, this::spinAnimController);

        controllerRegistrar.add(progressController);
        controllerRegistrar.add(stateController);
        controllerRegistrar.add(spinController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
