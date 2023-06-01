package net.fryke.tomesofpower.client.model;

import net.fryke.tomesofpower.ToPMod;
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
        Identifier textureId;

        // TODO how do we check the tome item state here?
//        if(((TomeItem) animatable).selectedSpell == SpellIdentifiers.EMBER_SPELL_ID) {
//            textureId = new Identifier(ToPMod.MOD_ID, "textures/item/texture1.png");
//        } else {
//            textureId = new Identifier(ToPMod.MOD_ID, "textures/item/texture2.png");
//        }
        return new Identifier(ToPMod.MOD_ID, "textures/item/testing_spell_page.png");
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return new Identifier(ToPMod.MOD_ID, "animations/spell_page.animation.json");
    }
}
