package net.fryke.tomesofpower.interfaces;

import net.minecraft.nbt.NbtCompound;

public interface BlockEntityDBTInterface {
    void setNbtData(NbtCompound dbtData);
    void getNbtData(NbtCompound nbt);
}
