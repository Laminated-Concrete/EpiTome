package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TomeModel extends GeoModel<TomeItem> {
    @Override
    public Identifier getModelResource(TomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/" + animatable.tomeId.getPath() + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(TomeItem animatable) {
//        return new Identifier(EpiTomeMod.MOD_ID, "textures/no_texture.png");
        return new Identifier(EpiTomeMod.MOD_ID, "textures/tomes/" + animatable.tomeId.getPath() + ".png");
    }

    @Override
    public Identifier getAnimationResource(TomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/tome.animation.json");
    }
}
