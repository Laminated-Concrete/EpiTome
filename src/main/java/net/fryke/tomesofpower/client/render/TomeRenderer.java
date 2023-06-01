package net.fryke.tomesofpower.client.render;

import net.fryke.tomesofpower.client.SpellPageAnimatable;
import net.fryke.tomesofpower.client.model.SpellPageLayer;
import net.fryke.tomesofpower.client.model.TomeModel;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TomeRenderer extends GeoItemRenderer<TomeItem> {
    public TomeRenderer(SpellPageAnimatable spellPageAnimatable) {
        super(new TomeModel());
        addRenderLayer(new SpellPageLayer(spellPageAnimatable));
    }
}
