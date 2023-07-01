package net.fryke.epitome.spells.types;

import net.fryke.epitome.client.sounds.SpellSoundEffect;
import net.fryke.epitome.client.sounds.TomeSoundEffect;
import net.fryke.epitome.item.tomes.TomeItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Spell {
    public Identifier SPELL_TYPE;
    public Identifier SPELL_ID;
    public int cooldownLengthTicks = 0;
    public int chargeTimeTicks = 0;
    public int lifetimeTicks = 0;
    public double spellRange = 0;
    protected SoundEvent on_cast_sound;

    public Spell() {

    }

    public Identifier getSpellType() {
        return SPELL_TYPE;
    }

    public Identifier getSpellId() {
        return SPELL_ID;
    }

    public void doCastSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        this.castSpell(world, caster, hand, tome);
    }

    public void doCastSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        if(on_cast_sound != null) {
            world.playSound(null, caster.getBlockPos(), on_cast_sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
//            MinecraftClient.getInstance().getSoundManager().play(on_cast_sound);
        }

        this.castSpellClient(world, caster, hand, tome);
    }

    public abstract void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome);
    public abstract void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome);
}
