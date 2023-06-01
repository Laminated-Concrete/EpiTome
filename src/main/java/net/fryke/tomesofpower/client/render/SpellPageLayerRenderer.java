package net.fryke.tomesofpower.client.render;

import net.fryke.tomesofpower.client.model.SpellPageModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;

public class SpellPageLayerRenderer extends GeoObjectRenderer {
    public SpellPageLayerRenderer() {
        super(new SpellPageModel());
    }
}
