package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.KnowledgeTomeModel;
import net.fryke.epitome.item.tomes.KnowledgeTomeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class KnowledgeTomeRenderer extends GeoItemRenderer<KnowledgeTomeItem> {
    public KnowledgeTomeRenderer() {
        super(new KnowledgeTomeModel());
    }
}
