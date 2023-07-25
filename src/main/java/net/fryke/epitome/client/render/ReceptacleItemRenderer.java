package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.ReceptacleItemModel;
import net.fryke.epitome.item.ReceptacleItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ReceptacleItemRenderer extends GeoItemRenderer<ReceptacleItem> {
    public ReceptacleItemRenderer() {
        super(new ReceptacleItemModel());
    }
}
