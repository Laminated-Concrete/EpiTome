package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.entity.ReceptacleBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ReceptacleBlockEntityModel extends GeoModel<ReceptacleBlockEntity> {
    @Override
    public Identifier getModelResource(ReceptacleBlockEntity animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/receptacle_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(ReceptacleBlockEntity animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "textures/block/receptacle_block.png");
    }

    @Override
    public Identifier getAnimationResource(ReceptacleBlockEntity animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/receptacle_block.animation.json");
    }
}
