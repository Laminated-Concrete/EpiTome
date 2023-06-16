package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class EmberSpellEntityModel extends GeoModel {
    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/ember_spell.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "textures/entity/ember_spell.png");
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return null;
    }
}
