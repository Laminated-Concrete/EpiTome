package net.fryke.epitome.rituals.types;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.tomes.TomeIdentifiers;
import net.fryke.epitome.rituals.RitualIdentifiers;
import net.fryke.epitome.rituals.RitualManager;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public class FireRitual extends Ritual {
    public FireRitual() {
        super();
        this.ritualId = RitualIdentifiers.FIRE_RITUAL_ID;
        this.tomeId = TomeIdentifiers.FIRE_TOME;

        // we want this one to be fast. they are lighting fires
        this.ritualTimeLimit = 200; // 10s
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
        // for this ritual, they must set all the netherrack blocks on fire
        RitualManager.RitualStructure structure = RitualManager.getInstance().getRitualStructure(ritualId);
        boolean allLit = true;

        for (RitualManager.BlockEntry entry : structure.blockEntries()) {
            if(Arrays.stream(entry.allowedBlockTypeIds()).anyMatch((id) -> id.equals("minecraft:netherrack"))) {
                // fire is actually considered a proper block by itself, so we want to target the coords above each netherrack block
                BlockPos targetPos = ritualBlockPos.add(entry.blockPosOffset()).add(0, 1, 0);
                if(world.getBlockState(targetPos).getBlock() != Blocks.FIRE) {
                    allLit = false;
                    break;
                }
            }
        }

        if(allLit) {
            ModLogger.log("Ritual condition fufilled");
            state = RitualStates.SUCCEEDED;
            finishedRitual();
        }
    }
}
