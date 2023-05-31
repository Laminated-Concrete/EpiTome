package net.fryke.tomesofpower.client.render;

import net.fryke.tomesofpower.client.model.TomeModel;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TomeRenderer extends GeoItemRenderer<TomeItem> {
    public TomeRenderer() {
        super(new TomeModel());
    }
}
