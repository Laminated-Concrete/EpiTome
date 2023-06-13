package net.fryke.tomesofpower.rituals;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.rituals.types.EarthRitual;
import net.fryke.tomesofpower.rituals.types.KnowledgeRitual;
import net.fryke.tomesofpower.rituals.types.Ritual;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModRituals {
    public static SimpleRegistry ritualRegistry = FabricRegistryBuilder.createSimple(Ritual.class, new Identifier(ToPMod.MOD_ID, "ritual_registry_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void registerModRituals() {
        ToPMod.LOGGER.info("Registering Mod Rituals for " + ToPMod.MOD_ID);
    }

    public static Ritual registerRitual(Identifier ritualIdentifier, Ritual ritual) {
        return Registry.register(ritualRegistry, ritualIdentifier, ritual);
    }

    public static final Ritual KNOWLEDGE_RITUAL = registerRitual(RitualIdentifiers.KNOWLEDGE_RITUAL_ID, new KnowledgeRitual());
    public static final Ritual EARTH_RITUAL = registerRitual(RitualIdentifiers.EARTH_RITUAL_ID, new EarthRitual());
}
