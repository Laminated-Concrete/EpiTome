package net.fryke.epitome.rituals;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.rituals.types.EarthRitual;
import net.fryke.epitome.rituals.types.KnowledgeRitual;
import net.fryke.epitome.rituals.types.Ritual;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModRituals {
    public static SimpleRegistry ritualRegistry = FabricRegistryBuilder.createSimple(Ritual.class, new Identifier(EpiTomeMod.MOD_ID, "ritual_registry_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void registerModRituals() {
        EpiTomeMod.LOGGER.info("Registering Mod Rituals for " + EpiTomeMod.MOD_ID);
    }

    public static Ritual registerRitual(Identifier ritualIdentifier, Ritual ritual) {
        return Registry.register(ritualRegistry, ritualIdentifier, ritual);
    }

    public static final Ritual KNOWLEDGE_RITUAL = registerRitual(RitualIdentifiers.KNOWLEDGE_RITUAL_ID, new KnowledgeRitual());
    public static final Ritual EARTH_RITUAL = registerRitual(RitualIdentifiers.EARTH_RITUAL_ID, new EarthRitual());
}
