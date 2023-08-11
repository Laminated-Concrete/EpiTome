package net.fryke.epitome.mixin;

import net.fryke.epitome.ModSounds;
import net.fryke.epitome.client.sounds.TomeSoundEffect;
import net.fryke.epitome.interfaces.IEntityNbtSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityClientMixin implements IEntityNbtSaver {
    private NbtCompound epitomeData;
    private TomeSoundEffect casting_loop_sound;
    private boolean needsEpitomeDataUpdate = false;

    @Shadow
    private World world;

    @Shadow
    public World getWorld() { return this.world; }

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
    protected void handleEpitomeEffects(CallbackInfo ci) {
        // This method is our hook into an entity's tick method
        // From here we can trigger various sound and particle effects?
        if(this.getWorld().isClient) {
            NbtCompound epitomeData = this.getEpitomeData();

            if(epitomeData.contains("is_casting")) {
                Boolean isCasting = epitomeData.getBoolean("is_casting");
                SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

                if(isCasting) {
                    if(!soundManager.isPlaying(casting_loop_sound)) {
                        if(casting_loop_sound == null) {
                            casting_loop_sound = new TomeSoundEffect((Entity)(Object)this, ModSounds.CASTING_LOOP_T1_EVENT, true, 0.7f);
                        }

                        soundManager.play(casting_loop_sound);
                    }
                } else {
                    if(soundManager.isPlaying(casting_loop_sound)) {
                        soundManager.stop(casting_loop_sound);
                        casting_loop_sound = null;
                    }
                }
            }
        }
    }
}
