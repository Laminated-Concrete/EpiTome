package net.fryke.tomesofpower.spells;

import net.fryke.tomesofpower.ToPMod;
import net.minecraft.util.Identifier;

public class SpellIdentifiers {
    // Spell type identifiers
    public static final Identifier INTERACTION_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "interaction_spell_type");
    public static final Identifier SELF_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "self_spell_type");
    public static final Identifier PROJECTILE_ENTITY_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "projectile_entity_spell_type");
    public static final Identifier CUSTOM_ENTITY_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "custom_entity_spell_type");


    // Used in the Tome of Earth (T1)
    public static final Identifier DIG_SPELL_ID = new Identifier(ToPMod.MOD_ID, "dig_spell");
    public static final Identifier PLOW_SPELL_ID = new Identifier(ToPMod.MOD_ID, "plow_spell");
    public static final Identifier PICK_UP_SPELL_ID = new Identifier(ToPMod.MOD_ID, "pick_up_spell");

    // Used in the Tome of Air (T1)
    public static final Identifier LAUNCH_SPELL_ID = new Identifier(ToPMod.MOD_ID, "launch_spell");
    public static final Identifier PULL_SPELL_ID = new Identifier(ToPMod.MOD_ID, "pull_spell");
    public static final Identifier PUSH_SPELL_ID = new Identifier(ToPMod.MOD_ID, "push_spell");

    // Used in the Tome of Fire (T1)
    public static final Identifier EMBER_SPELL_ID = new Identifier(ToPMod.MOD_ID, "ember_spell");
    public static final Identifier BURNING_GROUND_SPELL_ID = new Identifier(ToPMod.MOD_ID, "burning_ground_spell");
    public static final Identifier FLAME_DASH_SPELL_ID = new Identifier(ToPMod.MOD_ID, "flame_dash_spell");

    // Used in the Tome of Water (T1)
    public static final Identifier WATER_JUMP_SPELL_ID = new Identifier(ToPMod.MOD_ID, "water_jump_spell");
    public static final Identifier WATER_WALL_SPELL_ID = new Identifier(ToPMod.MOD_ID, "water_wall_spell");
    public static final Identifier EXTINGUISH_SPELL_ID = new Identifier(ToPMod.MOD_ID, "extinguish_spell");
}
