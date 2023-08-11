package net.fryke.epitome.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryke.epitome.interfaces.IEntityNbtSaver;
import net.fryke.epitome.packets.EpitomeDataUpdatePacket;
import net.fryke.epitome.packets.SwitchSpellPacket;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityServerMixin implements IEntityNbtSaver {
    private NbtCompound epitomeData;
    private boolean needsEpitomeDataUpdate = false;

    @Shadow public void populateCrashReport(CrashReportSection section) {}
    @Shadow public abstract UUID getUuid();

    @Override
    public NbtCompound getEpitomeData() {
        if(this.epitomeData == null) {
            this.epitomeData = new NbtCompound();
        }

        return this.epitomeData;
    }

    @Override
    public void setEpitomeData(NbtCompound epitomeData) {
        this.epitomeData = epitomeData;
    }

    @Override
    public void triggerEpitomeDataUpdate() {
        this.needsEpitomeDataUpdate = true;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeEpitomeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if(this.epitomeData != null) {
            nbt.put("epitome_data", this.epitomeData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readEpitomeNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("epitome_data")) {
            this.epitomeData = nbt.getCompound("epitome_data");
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    protected void handleEpitomeDataSync(CallbackInfo ci) {
        try {
            if (needsEpitomeDataUpdate) {
                EpitomeDataUpdatePacket packet = new EpitomeDataUpdatePacket(this.getEpitomeData(), this.getUuid());
                ServerPlayNetworking.send((ServerPlayerEntity)(Object)this, packet);
                for (ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerPlayerEntity) (Object) this)) {
                    ServerPlayNetworking.send(serverPlayerEntity, packet);
                }
                needsEpitomeDataUpdate = false;
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Ticking Epitome Entity Server Mixin");
            CrashReportSection crashReportSection = crashReport.addElement("Player being ticked");
            this.populateCrashReport(crashReportSection);
            throw new CrashException(crashReport);
        }
    }
}