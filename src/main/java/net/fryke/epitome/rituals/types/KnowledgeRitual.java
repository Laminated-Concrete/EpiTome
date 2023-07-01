package net.fryke.epitome.rituals.types;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.tomes.TomeIdentifiers;
import net.fryke.epitome.rituals.RitualIdentifiers;
import net.fryke.epitome.rituals.RitualManager;

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
        // TODO particle effects
    }
}
