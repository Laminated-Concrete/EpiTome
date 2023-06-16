package net.fryke.epitome.client.render;

import net.fryke.epitome.client.SpellPageAnimatable;
import net.fryke.epitome.client.model.SpellPageLayer;
import net.fryke.epitome.client.model.TomeModel;
import net.fryke.epitome.item.tomes.TomeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TomeRenderer extends GeoItemRenderer<TomeItem> {
    public TomeRenderer(SpellPageAnimatable spellPageAnimatable) {
        super(new TomeModel());
        addRenderLayer(new SpellPageLayer(spellPageAnimatable));
    }
}
