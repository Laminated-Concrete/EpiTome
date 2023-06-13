package net.fryke.tomesofpower.interfaces;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ScrollEvent {
    void onScroll(int scrollDirection, CallbackInfo callbackInfo);
}
