package net.fryke.epitome.client.render;

import net.fryke.epitome.client.model.ReceptacleBlockEntityModel;
import net.fryke.epitome.entity.ReceptacleBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ReceptacleBlockEntityRenderer extends GeoBlockRenderer<ReceptacleBlockEntity> {
    public ReceptacleBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new ReceptacleBlockEntityModel());
    }
}
