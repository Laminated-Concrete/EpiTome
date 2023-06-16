package net.fryke.epitome.client.render;

import net.fryke.epitome.entity.spell.custom.WaterWallSpellEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class WaterWallEntityRenderer extends EntityRenderer<WaterWallSpellEntity> {
    public WaterWallEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(WaterWallSpellEntity entity) {
        return null;
    }
}
