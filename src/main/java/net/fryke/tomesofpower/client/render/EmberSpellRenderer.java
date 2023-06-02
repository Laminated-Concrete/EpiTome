package net.fryke.tomesofpower.client.render;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.model.EmberSpellEntityModel;
import net.fryke.tomesofpower.entity.spell.projectile.EmberSpellEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EmberSpellRenderer extends GeoEntityRenderer<EmberSpellEntity> {

    public EmberSpellRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EmberSpellEntityModel());
    }

    @Override
    public Identifier getTextureLocation(EmberSpellEntity animatable) {
        return new Identifier(ToPMod.MOD_ID, "textures/entity/ember_spell.png");
    }
}
