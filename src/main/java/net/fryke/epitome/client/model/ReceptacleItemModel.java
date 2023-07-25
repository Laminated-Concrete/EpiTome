package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.ReceptacleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ReceptacleItemModel extends GeoModel<ReceptacleItem> {
    @Override
    public Identifier getModelResource(ReceptacleItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/receptacle_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(ReceptacleItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "textures/block/receptacle_block.png");
    }

    @Override
    public Identifier getAnimationResource(ReceptacleItem animatable) {
        return null;
    }
}
