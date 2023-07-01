package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.client.SpellPageAnimatable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SpellPageModel extends GeoModel {
    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/spell_page.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
//        ItemStack itemStack = ((SpellPageAnimatable) animatable).player.getMainHandStack();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        String spellTextureName = ((SpellPageAnimatable) animatable).getSpellTextureName();
        // this is the problem
        // somehow this method needs to know what the currently selected spell is for the
        // itemstack that the player is holding
        // ... but this model is connected to the animatable, which is connected to the
        // ITEM, not the item stack.
        // there is only one global ITEM, which means there is only one global animatable.
        // if we want the spell page model to render per-item stack, then we need to somehow store it on the item stack
        // fuck


//        if(player == null) {
            return new Identifier(EpiTomeMod.MOD_ID, "textures/spell_pages/" + spellTextureName + ".png");
//        } else {
//            ItemStack itemStack = MinecraftClient.getInstance().player.getMainHandStack();
//            if(itemStack.hasNbt()) {
//                NbtCompound nbt = itemStack.getNbt();
//                String pageName = new Identifier(nbt.getString("epitome.selectedSpell")).getPath();
//                return new Identifier(EpiTomeMod.MOD_ID, "textures/spell_pages/" + pageName + ".png");
//            } else {
//                return new Identifier(EpiTomeMod.MOD_ID, "textures/spell_pages/" + "blank" + ".png");
//            }
//        }
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/spell_page.animation.json");
    }
}
