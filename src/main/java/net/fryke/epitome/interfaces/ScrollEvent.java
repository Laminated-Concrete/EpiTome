package net.fryke.epitome.interfaces;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ScrollEvent {
    void onScroll(int scrollDirection, CallbackInfo callbackInfo);
}
