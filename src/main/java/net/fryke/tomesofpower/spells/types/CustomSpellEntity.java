package net.fryke.tomesofpower.spells.types;

import net.fryke.tomesofpower.ToPMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CustomSpellEntity extends Entity implements SpellInterface {
    public static final Identifier CUSTOM_ENTITY_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "custom_entity_spell_type");

    public CustomSpellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public Identifier getSpellType() {
        return CUSTOM_ENTITY_SPELL_TYPE;
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
