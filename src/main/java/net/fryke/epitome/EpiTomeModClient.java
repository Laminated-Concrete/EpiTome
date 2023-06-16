package net.fryke.epitome;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fryke.epitome.client.render.EmberSpellRenderer;
import net.fryke.epitome.entity.ModEntities;
import net.fryke.epitome.interfaces.ScrollEvent;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.particles.CustomFireSmokeParticle;
import net.fryke.epitome.particles.FallingUpWaterParticle;
import net.fryke.epitome.particles.ModParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import org.lwjgl.glfw.GLFW;

public class EpiTomeModClient implements ClientModInitializer {
    public static KeyBinding keyBinding;
    public static final Event<ScrollEvent> SCROLL_EVENT = EventFactory.createArrayBacked(ScrollEvent.class,
        (listeners) -> (scrollDirection, callbackInfo) -> {
            for (ScrollEvent listener : listeners) {
                listener.onScroll(scrollDirection, callbackInfo);
            }
        });

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.epitome.modifier_key", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "category.epitome.keybinds_category" // The translation key of the keybinding's category.
        ));

//
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (keyBinding.wasPressed()) {
//                Item heldItem = client.player.getInventory().getMainHandStack().getItem();
//                if(TomeItem.class.isAssignableFrom(heldItem.getClass())) {
//                    TomeItem tome = (TomeItem) heldItem;
//                    tome.switchSpell(1);
//                }
//            }
//        });

        SCROLL_EVENT.register((scrollDirection, callbackInfo) -> {
            // Now we have to be careful about this. We don't want to ruin the scrolling behavior
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.currentScreen == null && keyBinding.isPressed()) {
                Item heldItem = client.player.getInventory().getMainHandStack().getItem();
                if (TomeItem.class.isAssignableFrom(heldItem.getClass())) {
                    TomeItem tome = (TomeItem) heldItem;
                    tome.switchSpell(scrollDirection);
                    callbackInfo.cancel();
                }
            }
        });


        EntityRendererRegistry.register(ModEntities.EMBER_SPELL_ENTITY_TYPE, EmberSpellRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.CUSTOM_SMOKE, CustomFireSmokeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_UP_WATER, FallingUpWaterParticle.Factory::new);
    }
}
