package net.fryke.testingmod.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fryke.testingmod.TestingMod;
import net.fryke.testingmod.client.render.SpellEntityRenderer;
import net.fryke.testingmod.client.render.TestingProjectileEntityRenderer;
import net.fryke.testingmod.entity.custom.SpellEntity;
import net.fryke.testingmod.entity.custom.TestingProjectileEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<TestingProjectileEntity> TESTING_PROJECTILE_ENTITY_ENTITY_TYPE = register(
            "testing_projectile",
            FabricEntityTypeBuilder.<TestingProjectileEntity>create(SpawnGroup.MISC, TestingProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static final EntityType<SpellEntity> SPELL_ENTITY_TYPE = register(
            "spell_entity",
            FabricEntityTypeBuilder.<SpellEntity>create(SpawnGroup.MISC, SpellEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static void registerModEntities() {
        TestingMod.LOGGER.info("Registering Mod Entities for " + TestingMod.MOD_ID);

        EntityRendererRegistry.register(TESTING_PROJECTILE_ENTITY_ENTITY_TYPE,
                TestingProjectileEntityRenderer::new);

        EntityRendererRegistry.register(SPELL_ENTITY_TYPE,
                SpellEntityRenderer::new);
    }
    private static EntityType register(String name, EntityType entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(TestingMod.MOD_ID, name), entity);
    }
}
