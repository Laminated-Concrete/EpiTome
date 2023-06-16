package net.fryke.epitome.client.render;

import net.fryke.epitome.entity.spell.custom.BurningGroundSpellEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class BurningGroundEntityRenderer extends EntityRenderer<BurningGroundSpellEntity> {
    public BurningGroundEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BurningGroundSpellEntity entity) {
        return null;
    }
}
