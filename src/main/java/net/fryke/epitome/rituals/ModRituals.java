package net.fryke.epitome.rituals;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.rituals.types.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModRituals {
    public static final SimpleRegistry<Ritual> ritualRegistry = FabricRegistryBuilder.createSimple(Ritual.class, new Identifier(EpiTomeMod.MOD_ID, "ritual_registry_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void registerModRituals() {
        EpiTomeMod.LOGGER.info("Registering Mod Rituals for " + EpiTomeMod.MOD_ID);
    }

    public static Ritual registerRitual(Identifier ritualIdentifier, Ritual ritual) {
        return Registry.register(ritualRegistry, ritualIdentifier, ritual);
    }

    public static final Ritual KNOWLEDGE_RITUAL = registerRitual(RitualIdentifiers.KNOWLEDGE_RITUAL_ID, new KnowledgeRitual());
    public static final Ritual EARTH_RITUAL = registerRitual(RitualIdentifiers.EARTH_RITUAL_ID, new EarthRitual());
    public static final Ritual FIRE_RITUAL = registerRitual(RitualIdentifiers.FIRE_RITUAL_ID, new FireRitual());
    public static final Ritual AIR_RITUAL = registerRitual(RitualIdentifiers.AIR_RITUAL_ID, new AirRitual());
    public static final Ritual WATER_RITUAL = registerRitual(RitualIdentifiers.WATER_RITUAL_ID, new WaterRitual());
}
