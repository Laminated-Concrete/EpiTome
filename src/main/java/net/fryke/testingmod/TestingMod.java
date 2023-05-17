package net.fryke.testingmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fryke.testingmod.block.ModBlocks;
import net.fryke.testingmod.entity.ModEntities;
import net.fryke.testingmod.item.ModItems;
import net.fryke.testingmod.event.ServerConnectionInitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingMod implements ModInitializer {
	public static final String MOD_ID = "testingmod";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

// xd this is a funny comment!
//	public static final EntityType<TestingProjectileEntity> TESTING_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(
//			Registries.ENTITY_TYPE,
//			new Identifier(MOD_ID, "testing_projectile"),
//			FabricEntityTypeBuilder.<TestingProjectileEntity>create(SpawnGroup.MISC, TestingProjectileEntity::new)
//					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
//					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
//					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
//	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();

		ServerPlayConnectionEvents.INIT.register(new ServerConnectionInitHandler());
	}
}
