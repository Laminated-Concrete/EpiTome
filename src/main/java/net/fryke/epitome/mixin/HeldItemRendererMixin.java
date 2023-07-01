package net.fryke.epitome.mixin;

import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.ModItems;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Shadow
    private MinecraftClient client;

    @Shadow
    private ItemStack mainHand;

    @Shadow
    private float equipProgressMainHand;

    private boolean skipEquipOffset = false;
    private boolean skipTheSkip = false;

    @Inject(at = @At(value = "HEAD"), method = "applyEquipOffset", cancellable = true)
    private void customApplyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress, CallbackInfo ci) {
        // Hi there. Yes this is messy. Yes it is bleh. But... it fucking works. So bite me.
        // The whole purpose of this mess is to get rid of the 'equip' bobbing animation that
        //  happens when the NBT data of an item is updated and the itemStack gets re-made with the new data
        //  It took me like 4 hours to find this injection point and figure out the logic.
        //  It could be cleaner, but I'm scared to break it again. It works, so hurrah.
        //  As long as our logic is always wrapped in checks for our modded items, we should be fine.

        ClientPlayerEntity clientPlayerEntity = this.client.player;
        ItemStack itemStack = clientPlayerEntity.getMainHandStack();
//        ItemStack itemStack2 = clientPlayerEntity.getOffHandStack();

        if(!itemStack.isOf(this.mainHand.getItem())) {
            // we are in the middle of switching to a new item
            // so literally skip our skip UNTIL equipProgressMainHand is 1.0f again
            skipTheSkip = true;
        }

        if(skipTheSkip) {
            if(equipProgressMainHand == 1.0f) {
                skipTheSkip = false;
            }

            return;
        }

        // If we are holding a tome
        if(TomeItem.class.isAssignableFrom(this.mainHand.getItem().getClass())) {
            if (itemStack.isOf(this.mainHand.getItem()) && equipProgress > 0) {
                skipEquipOffset = true;
            }

            if (skipEquipOffset) {
                if (equipProgressMainHand == 1.0f) {
                    // if we are back at a 1.0f, then it has finished so we go back to normal
                    skipEquipOffset = false;
                }

                int i = arm == Arm.RIGHT ? 1 : -1;
                matrices.translate((float) i * 0.56f, -0.52f + 0.0f * -0.6f, -0.72f);
                ci.cancel();
            }
        } else {
            skipEquipOffset = false;
        }
    }
}
