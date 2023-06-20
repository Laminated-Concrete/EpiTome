package net.fryke.epitome.rituals.types;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.TomeIdentifiers;
import net.fryke.epitome.rituals.RitualIdentifiers;
import net.fryke.epitome.rituals.RitualManager;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Arrays;
import java.util.List;

public class AirRitual extends Ritual {
    public AirRitual() {
        super();
        this.ritualId = RitualIdentifiers.AIR_RITUAL_ID;
        this.tomeId = TomeIdentifiers.AIR_TOME;

        // they should get like 3-4 seconds per feather drop?
        // at the moment, the ritual consists of 7 target positions
        // so like 28 seconds total, 560 ticks
        this.ritualTimeLimit = 560; // 28s
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
        // for this ritual, they must drop feather items on each light blue wool block
        RitualManager.RitualStructure structure = RitualManager.getInstance().getRitualStructure(ritualId);
        boolean allDropped = true;

        for (RitualManager.BlockEntry entry : structure.blockEntries()) {
            if(Arrays.stream(entry.allowedBlockTypeIds()).anyMatch((id) -> id.equals("minecraft:light_blue_wool"))) {
                // we want to look at the position above the wool blocks, so we want to target the coords above each one
                BlockPos targetPos = ritualBlockPos.add(entry.blockPosOffset()).add(0, 1, 0);

                Box box = new Box(
                    targetPos.getX(), targetPos.getY(), targetPos.getZ(),
                    targetPos.getX() + 1, targetPos.getY() + 1, targetPos.getZ() + 1);
                EpiTomeMod.LOGGER.info("target box = " + box);

                List<Entity> entities = world.getOtherEntities(caster, box);
                boolean hasAFeather = false;
                if(!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        if(entity instanceof ItemEntity itemEntity) {
                            EpiTomeMod.LOGGER.info(itemEntity.getStack().getItem().toString());
                            if(itemEntity.getStack().getItem() == Items.FEATHER) {
                                hasAFeather = true;
                            }
                        }
                    }
                }

                if(!hasAFeather) {
                    EpiTomeMod.LOGGER.info("no feathers at = " + targetPos);
                    allDropped = false;
                    break;
                }
            }
        }

        if(allDropped) {
            EpiTomeMod.LOGGER.info("Ritual condition fufilled");
            state = RitualStates.SUCCEEDED;
            finishedRitual();
        }
    }
}
