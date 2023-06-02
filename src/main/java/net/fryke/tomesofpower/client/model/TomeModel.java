package net.fryke.tomesofpower.client.model;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.SpellPageAnimatable;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TomeModel extends GeoModel<TomeItem> {
    @Override
    public Identifier getModelResource(TomeItem animatable) {
        return new Identifier(ToPMod.MOD_ID, "geo/tome.geo.json");
    }

    @Override
    public Identifier getTextureResource(TomeItem animatable) {
        Identifier textureId = new Identifier(ToPMod.MOD_ID, "textures/tomes/" + animatable.tomeId.getPath() + ".png");
        ToPMod.LOGGER.info(textureId.toString());
        return textureId;
    }

    @Override
    public Identifier getAnimationResource(TomeItem animatable) {
        return new Identifier(ToPMod.MOD_ID, "animations/tome.animation.json");
    }
}
