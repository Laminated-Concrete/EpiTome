package net.fryke.epitome.interfaces;

import net.minecraft.nbt.NbtCompound;

public interface BlockEntityNBTInterface {
    void setNbtData(NbtCompound dbtData);
    void getNbtData(NbtCompound nbt);
}
