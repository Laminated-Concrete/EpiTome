package net.fryke.epitome.rituals.types;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.entity.RitualBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public abstract class Ritual {
    public enum RitualStates {
        NOT_STARTED,
        RUNNING,
        SUCCEEDED,
        FAILED,
    }

    public enum RitualTypes {
        RESOURCE,
        COMBAT,
        BOSS,
        SPELL,
    }

    public Identifier ritualId;
    public RitualTypes type;
    public RitualStates state = RitualStates.NOT_STARTED;
    public int ritualTimeLimit = 1;
    public int timeRemaining = -1;
    public boolean hasTask = true;
    public PlayerEntity caster;
    public BlockState ritualBlockState;
    public BlockPos ritualBlockPos;
    public World world;
    public RitualBlockEntity blockEntity;
    public Identifier tomeId;
    public Map<String, BlockState> blockStateMap = new HashMap<>(); // Block or BlockState?

    public Ritual() {

    }

    public void setData(PlayerEntity caster, BlockState blockState, BlockPos blockPos, World world, RitualBlockEntity blockEntity) {
        this.caster = caster;
        this.ritualBlockState = blockState;
        this.ritualBlockPos = blockPos;
        this.world = world;
        this.blockEntity = blockEntity;
    }


    public void startRitual() {
        if(state == RitualStates.NOT_STARTED) {
            EpiTomeMod.LOGGER.info("ritual state was not started, am starting now");
            state = RitualStates.RUNNING;
            this.onStarted();
        }
    }

    public void tick() {
        EpiTomeMod.LOGGER.info("IRitual tick = " + this.ritualBlockPos);
        if(state == RitualStates.RUNNING) {
            if(timeRemaining == -1) {
                EpiTomeMod.LOGGER.info("Initing ritual timer");
                timeRemaining = ritualTimeLimit;
            }
            timeRemaining--;
            EpiTomeMod.LOGGER.info("Ticks remaining = " + timeRemaining);

            this.onTick();

            if(timeRemaining == 0) {
                EpiTomeMod.LOGGER.info("Time ran out");
                if(hasTask) {
                    // if we have a task to be completed and time ran out, we failed
                    state = RitualStates.FAILED;
                } else {
                    // otherwise there was no task for this ritual, so we succeed
                    state = RitualStates.SUCCEEDED;
                }
                finishedRitual();
            }
        }
    }

    public void finishedRitual() {
        if(state == RitualStates.SUCCEEDED) {
            // spawn the tome?
            EpiTomeMod.LOGGER.info("Ritual succeeded. trying to spawn item");

            // The ritual succeeded, so we insert the resulting tome into the ritual block
            blockEntity.insertTome(this.tomeId);

        } else if(state == RitualStates.FAILED) {
            this.onFailed();
            state = RitualStates.NOT_STARTED;

            blockEntity.cancelRitual();
        }
    }

    public abstract Boolean canStart();
    public abstract void onStarted();
    public abstract void onFailed();
    public abstract void onTick();
}
