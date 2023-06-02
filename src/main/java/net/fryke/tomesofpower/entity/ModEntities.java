package net.fryke.tomesofpower.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.render.BurningGroundEntityRenderer;
import net.fryke.tomesofpower.client.render.TestingProjectileEntityRenderer;
import net.fryke.tomesofpower.entity.spell.custom.BurningGroundSpellEntity;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.entity.spell.projectile.EmberSpellEntity;
import net.fryke.tomesofpower.entity.custom.TestingProjectileEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TestingProjectileEntity> TESTING_PROJECTILE_ENTITY_ENTITY_TYPE = registerEntity(
            "testing_projectile",
            FabricEntityTypeBuilder.<TestingProjectileEntity>create(SpawnGroup.MISC, TestingProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static final EntityType<EmberSpellEntity> EMBER_SPELL_ENTITY_TYPE = registerSpellEntity(SpellIdentifiers.EMBER_SPELL_ID,
            FabricEntityTypeBuilder.<EmberSpellEntity>create(SpawnGroup.MISC, EmberSpellEntity::new).build()
    );

    public static final EntityType<BurningGroundSpellEntity> BURNING_GROUND_SPELL_ENTITY_TYPE = registerSpellEntity(SpellIdentifiers.BURNING_GROUND_SPELL_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BurningGroundSpellEntity::new).build()
    );

    public static void registerModEntities() {
        ToPMod.LOGGER.info("Registering Mod Entities for " + ToPMod.MOD_ID);

        EntityRendererRegistry.register(TESTING_PROJECTILE_ENTITY_ENTITY_TYPE,
                TestingProjectileEntityRenderer::new);

        EntityRendererRegistry.register(BURNING_GROUND_SPELL_ENTITY_TYPE,
                BurningGroundEntityRenderer::new);
    }

    private static EntityType registerSpellEntity(Identifier spellIdentifier, EntityType type) {
        return Registry.register(Registries.ENTITY_TYPE, spellIdentifier, type);
    }

    private static EntityType registerEntity(String name, EntityType entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(ToPMod.MOD_ID, name), entity);
    }
}
