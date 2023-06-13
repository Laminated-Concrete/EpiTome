package net.fryke.tomesofpower.rituals.types;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.item.tomes.TomeIdentifiers;
import net.fryke.tomesofpower.rituals.RitualHelpers;
import net.fryke.tomesofpower.rituals.RitualIdentifiers;
import net.fryke.tomesofpower.rituals.RitualStructures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class KnowledgeRitual extends Ritual {
    public KnowledgeRitual() {
        super();
        this.ritualId = RitualIdentifiers.KNOWLEDGE_RITUAL_ID;
        this.tomeId = TomeIdentifiers.FIRE_TOME;
    }

    @Override
    public Boolean canStart() {
        Boolean validStructure = RitualHelpers.checkStructure(world, ritualBlockPos, RitualStructures.EARTH_RITUAL_START_ID);

        if(!validStructure) {
            ToPMod.LOGGER.info("Ritual cannot start");
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
        BlockPos firstTargetPos = ritualBlockPos.add(-2, 0, -2);
        BlockState firstBlockState = this.world.getBlockState(firstTargetPos);

        if(firstBlockState.getBlock() == Blocks.OBSIDIAN) {
            ToPMod.LOGGER.info("Ritual condition fufilled");
            state = RitualStates.SUCCEEDED;
            finishedRitual();
        }
    }
}
