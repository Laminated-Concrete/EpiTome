package net.fryke.epitome.spells;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.spells.custom_entity.BurningGroundSpell;
import net.fryke.epitome.spells.custom_entity.WaterWallSpell;
import net.fryke.epitome.spells.interaction.*;
import net.fryke.epitome.spells.projectile.EmberSpell;
import net.fryke.epitome.spells.self.ExtinguishSpell;
import net.fryke.epitome.spells.self.FlameDashSpell;
import net.fryke.epitome.spells.self.WaterJumpSpell;
import net.fryke.epitome.spells.types.Spell;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ModSpells {
    public static final SimpleRegistry<Spell> spellRegistry = FabricRegistryBuilder.createSimple(Spell.class, new Identifier(EpiTomeMod.MOD_ID, "spell_registry_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void registerModSpells() {
        EpiTomeMod.LOGGER.info("Registering Mod Spells for " + EpiTomeMod.MOD_ID);
    }

    public static Spell registerSpell(Identifier spellIdentifier, Spell spell) {
        return Registry.register(spellRegistry, spellIdentifier, spell);
    }

    /// Spell registrations
    public static final Spell DIG_SPELL = registerSpell(SpellIdentifiers.DIG_SPELL_ID, new DigSpell());
    public static final Spell PLOW_SPELL = registerSpell(SpellIdentifiers.PLOW_SPELL_ID, new PlowSpell());
    public static final Spell PICK_UP_SPELL = registerSpell(SpellIdentifiers.PICK_UP_SPELL_ID, new PickUpSpell());

    public static final Spell LAUNCH_SPELL = registerSpell(SpellIdentifiers.LAUNCH_SPELL_ID, new LaunchSpell());
    public static final Spell PUSH_SPELL = registerSpell(SpellIdentifiers.PUSH_SPELL_ID, new PushSpell());
    public static final Spell PULL_SPELL = registerSpell(SpellIdentifiers.PULL_SPELL_ID, new PullSpell());

    public static final Spell EMBER_SPELL = registerSpell(SpellIdentifiers.EMBER_SPELL_ID, new EmberSpell());
    public static final Spell BURNING_GROUND_SPELL = registerSpell(SpellIdentifiers.BURNING_GROUND_SPELL_ID, new BurningGroundSpell());
    public static final Spell FLAME_DASH_SPELL = registerSpell(SpellIdentifiers.FLAME_DASH_SPELL_ID, new FlameDashSpell());

    public static final Spell WATER_WALL_SPELL = registerSpell(SpellIdentifiers.WATER_WALL_SPELL_ID, new WaterWallSpell());
    public static final Spell EXTINGUISH_SPELL = registerSpell(SpellIdentifiers.EXTINGUISH_SPELL_ID, new ExtinguishSpell());
    public static final Spell WATER_JUMP_SPELL = registerSpell(SpellIdentifiers.WATER_JUMP_SPELL_ID, new WaterJumpSpell());
}
