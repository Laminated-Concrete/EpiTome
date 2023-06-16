package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.SpellPageModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public class SpellPageLayerRenderer extends GeoObjectRenderer {
    public SpellPageLayerRenderer() {
        super(new SpellPageModel());
    }
}
