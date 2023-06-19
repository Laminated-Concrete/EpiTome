package net.fryke.epitome.mixin;

import net.fryke.epitome.EpiTomeModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow
    private double eventDeltaWheel;

    @Inject(method = "onMouseScroll", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void beforeMouseScrollEvent(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        // This chunk is ripped from Mouse.java and is used to determine the direction of the mousewheel
        double d = (client.options.getDiscreteMouseScroll().getValue() ? Math.signum(vertical) : vertical) * client.options.getMouseWheelSensitivity().getValue();
        if (this.eventDeltaWheel != 0.0 && Math.signum(d) != Math.signum(this.eventDeltaWheel)) {
            this.eventDeltaWheel = 0.0;
        }
        this.eventDeltaWheel += d;
        int i = (int)this.eventDeltaWheel;
        if (i == 0) {
            return;
        }
        this.eventDeltaWheel -= i;

        // Call our custom event, so we can do as we please
        EpiTomeModClient.SCROLL_EVENT.invoker().onScroll(i, ci);
    }
}
