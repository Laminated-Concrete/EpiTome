package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.TomeModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public class ReceptacleTomeLayerRenderer extends GeoObjectRenderer {
    public ReceptacleTomeLayerRenderer() {
        super(new TomeModel());
    }
}
