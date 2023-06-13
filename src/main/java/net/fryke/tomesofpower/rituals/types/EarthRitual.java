package net.fryke.tomesofpower.rituals.types;

public class EarthRitual extends Ritual {
    @Override
    public Boolean canStart() {
//        BlockPos targetPos = ritualBlockPos.add(2, 0, 2);
//        if(!RitualHelpers.isBlockType(world, targetPos, Blocks.OBSIDIAN)) {
//            return false;
//        }
//
//        targetPos = ritualBlockPos.add(-2, 0, 2);
//        if(!RitualHelpers.isBlockType(world, targetPos, Blocks.OBSIDIAN)) {
//            return false;
//        }
//
//        targetPos = ritualBlockPos.add(2, 0, -2);
//        if(!RitualHelpers.isBlockType(world, targetPos, Blocks.OBSIDIAN)) {
//            return false;
//        }
//
//        targetPos = ritualBlockPos.add(-2, 0, -2);
//        if(!RitualHelpers.isBlockType(world, targetPos, Blocks.OBSIDIAN)) {
//            return false;
//        }




        return true;
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onTick() {

    }
}
