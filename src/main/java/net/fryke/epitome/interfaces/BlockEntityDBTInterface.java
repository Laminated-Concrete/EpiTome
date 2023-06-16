package net.fryke.epitome.interfaces;

import net.minecraft.nbt.NbtCompound;

public interface BlockEntityDBTInterface {
    void setNbtData(NbtCompound dbtData);
    void getNbtData(NbtCompound nbt);
}
