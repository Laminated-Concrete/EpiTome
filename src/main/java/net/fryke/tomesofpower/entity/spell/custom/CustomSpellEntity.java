package net.fryke.tomesofpower.entity.spell.custom;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class CustomSpellEntity extends Entity {
    protected Spell spell;
    protected int lifetimeTicks = -1;
    protected int remainingLifetimeTicks = -1;
    protected int tickCounter = 0;

    public CustomSpellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void setLifetimeTicks(int lifetimeTicks) {
        this.lifetimeTicks = lifetimeTicks;
        this.remainingLifetimeTicks = lifetimeTicks;
    }

    @Override
    public void tick() {
        super.tick();
        tickCounter++;

        if(lifetimeTicks > 0 && remainingLifetimeTicks == 0) {
            // if we have a lifetime set and we are out of lifetime
            kill(); // kill the entity
        } else if(lifetimeTicks > 0) {
            // otherwise if we still have a lifetime then we need to keep track of the remainingLifeTime
            remainingLifetimeTicks--;
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
