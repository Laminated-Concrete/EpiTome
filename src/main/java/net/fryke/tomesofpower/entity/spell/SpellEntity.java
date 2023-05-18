package net.fryke.tomesofpower.entity.spell;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fryke.tomesofpower.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.UUID;

public class SpellEntity extends Entity {
    public SpellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Environment(EnvType.CLIENT)
    public SpellEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(ModEntities.SPELL_ENTITY_TYPE, world);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
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
