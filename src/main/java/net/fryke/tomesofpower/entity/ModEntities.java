package net.fryke.tomesofpower.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.render.SpellEntityRenderer;
import net.fryke.tomesofpower.client.render.TestingProjectileEntityRenderer;
import net.fryke.tomesofpower.entity.spell.ProjectileSpellEntity;
import net.fryke.tomesofpower.entity.spell.SpellEntity;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.entity.spell.EmberSpellEntity;
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

    public static final EntityType<ProjectileSpellEntity> SPELL_ENTITY_TYPE = registerEntity(
            "spell_entity",
            FabricEntityTypeBuilder.<ProjectileSpellEntity>create(SpawnGroup.MISC, ProjectileSpellEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static final EntityType<EmberSpellEntity> EMBER_SPELL_ENTITY_TYPE = registerSpellEntity(
            SpellIdentifiers.EMBER_SPELL_ID,
            FabricEntityTypeBuilder.<ProjectileSpellEntity>create(SpawnGroup.MISC, EmberSpellEntity::new)
//                    .trackedUpdateRate(0) // this is how many ticks pass before an update. so 1 is every tick
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static void registerModEntities() {
        ToPMod.LOGGER.info("Registering Mod Entities for " + ToPMod.MOD_ID);

        EntityRendererRegistry.register(TESTING_PROJECTILE_ENTITY_ENTITY_TYPE,
                TestingProjectileEntityRenderer::new);

        EntityRendererRegistry.register(EMBER_SPELL_ENTITY_TYPE,
                SpellEntityRenderer::new);
    }

    private static EntityType registerSpellEntity(Identifier spellIdentifier, EntityType entity) {
        return Registry.register(Registries.ENTITY_TYPE, spellIdentifier, entity);
    }

    private static EntityType registerEntity(String name, EntityType entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(ToPMod.MOD_ID, name), entity);
    }
}
