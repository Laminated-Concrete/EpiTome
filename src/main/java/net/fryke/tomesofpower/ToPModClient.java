package net.fryke.tomesofpower;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fryke.tomesofpower.client.render.EmberSpellRenderer;
import net.fryke.tomesofpower.entity.ModEntities;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ToPModClient implements ClientModInitializer {
    public static KeyBinding keyBinding;
    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tomesofpower.testingkey", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_M, // The keycode of the key
                "category.tomesofpower.test" // The translation key of the keybinding's category.
        ));

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (keyBinding.wasPressed()) {
//                client.player.sendMessage(Text.literal("Key was pressed!"), false);
//            }
//        });

        EntityRendererRegistry.register(ModEntities.EMBER_SPELL_ENTITY_TYPE, EmberSpellRenderer::new);
    }
}
