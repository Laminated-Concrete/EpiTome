package net.fryke.epitome;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryke.epitome.block.ModBlocks;
import net.fryke.epitome.commands.ModCommands;
import net.fryke.epitome.effects.ModEffects;
import net.fryke.epitome.entity.ModEntities;
import net.fryke.epitome.item.ModItemGroup;
import net.fryke.epitome.item.ModItems;
import net.fryke.epitome.event.ServerConnectionInitHandler;
import net.fryke.epitome.particles.ModParticles;
import net.fryke.epitome.rituals.ModRituals;
import net.fryke.epitome.rituals.RitualManager;
import net.fryke.epitome.spells.ModSpells;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;
import java.util.HashMap;
import java.util.Map;

public class EpiTomeMod implements ModInitializer {
	public static final String MOD_ID = "epitome";
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
		ModItemGroup.registerModItemGroups();
		ModEntities.registerModEntities();
		ModSpells.registerModSpells();
		ModEffects.registerModEffects();
		ModParticles.registerModParticles();
		ModRituals.registerModRituals();
		ModCommands.initializeModCommands();
		RitualManager ritualManager = RitualManager.getInstance();

		ServerPlayConnectionEvents.INIT.register(new ServerConnectionInitHandler());


		// This sets up our custom resource path and whatnot. Final path is "resources/data/epitome/rituals/file-name.json"
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return new Identifier(MOD_ID, "epitome_resources");
			}

			@Override
			public void reload(ResourceManager manager) {
				Map<Identifier, JsonElement> results = new HashMap<>();
				// we use the JsonDataLoader because it already has everything we need
				JsonDataLoader.load(manager, "rituals", new Gson(), results); // the 'dataType' param here is actually the target folder name
				LOGGER.info("Trying to reload json DATA = " + results.entrySet());
				// the loaded data is placed in the results object, so we iterate over that
				for (Map.Entry<Identifier, JsonElement> entry : results.entrySet()) {
					// From here we pass the entry data directly over to our ritual structure register logic
					ritualManager.parseRitual(entry.getKey(), entry.getValue());
				}
			}
		});
	}
}
