package net.fryke.epitome.rituals.types;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.TomeIdentifiers;
import net.fryke.epitome.rituals.RitualIdentifiers;
import net.fryke.epitome.rituals.RitualManager;
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
        Boolean validStructure = RitualManager.getInstance().checkStructure(world, ritualBlockPos, RitualManager.KNOWLEDGE_RITUAL_ID);

        if(!validStructure) {
            EpiTomeMod.LOGGER.info("Ritual cannot start");
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
            EpiTomeMod.LOGGER.info("Ritual condition fufilled");
            state = RitualStates.SUCCEEDED;
            finishedRitual();
        }
    }
}
