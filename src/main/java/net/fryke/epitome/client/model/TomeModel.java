package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TomeModel extends GeoModel<TomeItem> {
    @Override
    public Identifier getModelResource(TomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/tome.geo.json");
    }

    @Override
    public Identifier getTextureResource(TomeItem animatable) {
        Identifier textureId = new Identifier(EpiTomeMod.MOD_ID, "textures/tomes/" + animatable.tomeId.getPath() + ".png");
        return textureId;
    }

    @Override
    public Identifier getAnimationResource(TomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/tome.animation.json");
    }
}
