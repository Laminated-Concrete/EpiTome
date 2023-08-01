package net.fryke.epitome.client.model;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.KnowledgeTomeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class KnowledgeTomeModel extends GeoModel<KnowledgeTomeItem> {
    @Override
    public Identifier getModelResource(KnowledgeTomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "geo/knowledge_tome.geo.json");
    }

    @Override
    public Identifier getTextureResource(KnowledgeTomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "textures/tomes/knowledge_tome.png");
    }

    @Override
    public Identifier getAnimationResource(KnowledgeTomeItem animatable) {
        return new Identifier(EpiTomeMod.MOD_ID, "animations/tome.animation.json");
    }
}