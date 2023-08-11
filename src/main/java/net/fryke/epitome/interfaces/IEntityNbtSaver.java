package net.fryke.epitome.interfaces;

import net.minecraft.nbt.NbtCompound;

public interface IEntityNbtSaver {
    NbtCompound getEpitomeData();
    void setEpitomeData(NbtCompound epitomeData);
    void triggerEpitomeDataUpdate();
}
