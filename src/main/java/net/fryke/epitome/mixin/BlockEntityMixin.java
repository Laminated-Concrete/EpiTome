package net.fryke.epitome.mixin;

import net.fryke.epitome.interfaces.BlockEntityNBTInterface;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements BlockEntityNBTInterface {
    @Shadow
    protected void writeNbt(NbtCompound nbt) {}

    @Shadow
    public void readNbt(NbtCompound nbt) {}

    public void setNbtData(NbtCompound dbtData) {
        this.readNbt(dbtData);
    }

    public void getNbtData(NbtCompound nbt) { this.writeNbt(nbt); }
}
