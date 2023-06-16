package net.fryke.epitome.rituals.types;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.block.RitualBlock;
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
    public PlayerEntity caster;
    public BlockState ritualBlockState;
    public BlockPos ritualBlockPos;
    public World world;
    public Identifier tomeId;
    public Map<String, BlockState> blockStateMap = new HashMap<>(); // Block or BlockState?

    public Ritual() {

    }

    public void setData(PlayerEntity caster, BlockState blockState, BlockPos blockPos, World world) {
        this.caster = caster;
        this.ritualBlockState = blockState;
        this.ritualBlockPos = blockPos;
        this.world = world;
    }


    public void startRitual() {
        if(state == RitualStates.NOT_STARTED) {
            EpiTomeMod.LOGGER.info("ritual state was not started, am starting now");
            state = RitualStates.RUNNING;
            this.onStarted();
        }
    }

    public void tick() {
        if(state == RitualStates.RUNNING) {
            this.onTick();
        }
    }

    public void finishedRitual() {
        if(state == RitualStates.SUCCEEDED) {
            // spawn the tome?
            EpiTomeMod.LOGGER.info("Ritual succeeded. trying to spawn item");

            // The ritual succeeded, so we insert the resulting tome into the ritual block
            RitualBlock ritualBlock = (RitualBlock) ritualBlockState.getBlock();
            ritualBlock.insertTome(this.tomeId);

        } else if(state == RitualStates.FAILED) {
            this.onFailed();
        }
    }

    public abstract Boolean canStart();
    public abstract void onStarted();
    public abstract void onFailed();
    public abstract void onTick();
}
