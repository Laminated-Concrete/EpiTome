package net.fryke.tomesofpower;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fryke.tomesofpower.block.ModBlocks;
import net.fryke.tomesofpower.effects.ModEffects;
import net.fryke.tomesofpower.entity.ModEntities;
import net.fryke.tomesofpower.item.ModItems;
import net.fryke.tomesofpower.event.ServerConnectionInitHandler;
import net.fryke.tomesofpower.particles.ModParticles;
import net.fryke.tomesofpower.rituals.ModRituals;
import net.fryke.tomesofpower.rituals.RitualStructures;
import net.fryke.tomesofpower.spells.ModSpells;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class ToPMod implements ModInitializer {
	public static final String MOD_ID = "tomesofpower";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		GeckoLib.initialize();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModSpells.registerModSpells();
		ModEffects.registerModEffects();
		ModParticles.registerModParticles();
		ModRituals.registerModRituals();
		RitualStructures.registerModRitualStructures();

		ServerPlayConnectionEvents.INIT.register(new ServerConnectionInitHandler());
	}
}
