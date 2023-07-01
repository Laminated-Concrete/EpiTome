package net.fryke.epitome.rituals.types;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.tomes.TomeIdentifiers;
import net.fryke.epitome.rituals.RitualIdentifiers;
import net.fryke.epitome.rituals.RitualManager;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class EarthRitual extends Ritual {
    public EarthRitual() {
        super();
        this.ritualId = RitualIdentifiers.EARTH_RITUAL_ID;
        this.tomeId = TomeIdentifiers.EARTH_TOME;

        // considering we have 15 blocks of obsidian that must be mined
        // and it takes like ~10 seconds per block
        // and then we want to account for them switching targets
        // we give them like 170 seconds = 3,400 ticks
        this.ritualTimeLimit = 3400; // 170s
    }

    @Override
    public Boolean canStart() {
        Boolean validStructure = RitualManager.getInstance().checkStructure(world, ritualBlockPos, ritualId);

        if(!validStructure) {
            ModLogger.log("Ritual cannot start");
        }

        return validStructure;
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onTick() {
        // for this ritual, they must break the obsidian blocks
        RitualManager.RitualStructure structure = RitualManager.getInstance().getRitualStructure(ritualId);
        boolean allBroken = true;

        for (RitualManager.BlockEntry entry : structure.blockEntries()) {
            if(Arrays.stream(entry.allowedBlockTypeIds()).anyMatch((id) -> id.equals("minecraft:obsidian"))) {
                // we just check if the block is still obsidian, if it is then we fail the check and break
                BlockPos targetPos = ritualBlockPos.add(entry.blockPosOffset());
                if(world.getBlockState(targetPos).getBlock() == Blocks.OBSIDIAN) {
                    allBroken = false;
                    break;
                }
            }
        }

        if(allBroken) {
            ModLogger.log("Ritual condition fufilled");
            state = RitualStates.SUCCEEDED;
            finishedRitual();
        }
    }
}
