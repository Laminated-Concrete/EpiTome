package net.fryke.tomesofpower.spells;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.spells.interaction.DigSpell;
import net.fryke.tomesofpower.spells.projectile.EmberSpell;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModSpells {
    public static SimpleRegistry spellRegistry = FabricRegistryBuilder.createSimple(Spell.class, new Identifier(ToPMod.MOD_ID, "spell_registry_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();;

    public static void registerModSpells() {
        ToPMod.LOGGER.info("Registering Mod Spells for " + ToPMod.MOD_ID);
    }

    public static Spell registerSpell(Identifier spellIdentifier, Spell spell) {
        return Registry.register(spellRegistry, spellIdentifier, spell);
    }

    /// Spell registrations
    public static final Spell DIG_SPELL = registerSpell(SpellIdentifiers.DIG_SPELL_ID, new DigSpell());
    public static final Spell EMBER_SPELL = registerSpell(SpellIdentifiers.EMBER_SPELL_ID, new EmberSpell());
}