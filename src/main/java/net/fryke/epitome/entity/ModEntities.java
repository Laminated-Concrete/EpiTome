package net.fryke.epitome.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.client.render.BurningGroundEntityRenderer;
import net.fryke.epitome.client.render.TestingProjectileEntityRenderer;
import net.fryke.epitome.client.render.WaterWallEntityRenderer;
import net.fryke.epitome.entity.spell.custom.BurningGroundSpellEntity;
import net.fryke.epitome.entity.spell.custom.WaterWallSpellEntity;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.entity.spell.projectile.EmberSpellEntity;
import net.fryke.epitome.entity.custom.TestingProjectileEntity;
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

    public static final EntityType<WaterWallSpellEntity> WATER_WALL_SPELL_ENTITY_TYPE = registerSpellEntity(SpellIdentifiers.WATER_WALL_SPELL_ID,
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, WaterWallSpellEntity::new).build()
    );

    public static void registerModEntities() {
        EpiTomeMod.LOGGER.info("Registering Mod Entities for " + EpiTomeMod.MOD_ID);

        EntityRendererRegistry.register(TESTING_PROJECTILE_ENTITY_ENTITY_TYPE,
                TestingProjectileEntityRenderer::new);

        EntityRendererRegistry.register(BURNING_GROUND_SPELL_ENTITY_TYPE,
                BurningGroundEntityRenderer::new);

        EntityRendererRegistry.register(WATER_WALL_SPELL_ENTITY_TYPE,
                WaterWallEntityRenderer::new);
    }

    private static EntityType registerSpellEntity(Identifier spellIdentifier, EntityType type) {
        return Registry.register(Registries.ENTITY_TYPE, spellIdentifier, type);
    }

    private static EntityType registerEntity(String name, EntityType entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(EpiTomeMod.MOD_ID, name), entity);
    }
}
