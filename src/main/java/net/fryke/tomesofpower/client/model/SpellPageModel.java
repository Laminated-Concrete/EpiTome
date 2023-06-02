package net.fryke.tomesofpower.client.model;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.SpellPageAnimatable;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SpellPageModel extends GeoModel {
    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return new Identifier(ToPMod.MOD_ID, "geo/spell_page.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
        Identifier textureId = new Identifier(ToPMod.MOD_ID, "textures/spell_pages/" + ((SpellPageAnimatable) animatable).tome.selectedSpell.getPath() + ".png");
        return textureId;
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return new Identifier(ToPMod.MOD_ID, "animations/spell_page.animation.json");
    }
}
