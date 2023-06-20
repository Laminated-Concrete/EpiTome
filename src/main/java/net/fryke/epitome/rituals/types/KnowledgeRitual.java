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
        this.ritualTimeLimit = 100; // 5s
        this.hasTask = false;
    }

    @Override
    public Boolean canStart() {
        Boolean validStructure = RitualManager.getInstance().checkStructure(world, ritualBlockPos, ritualId);

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
        // TODO particle effects
    }
}
