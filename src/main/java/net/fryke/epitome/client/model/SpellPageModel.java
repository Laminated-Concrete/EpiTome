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
        String spellTextureName = ((SpellPageAnimatable) animatable).getSpellTextureName();

        if(spellTextureName != null) {
            return new Identifier(EpiTomeMod.MOD_ID, "textures/spell_pages/" + spellTextureName + ".png");
        } else {
            return new Identifier(EpiTomeMod.MOD_ID, "textures/no_texture.png");
        }
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/spell_page.animation.json");
    }
}
