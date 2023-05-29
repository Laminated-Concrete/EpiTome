package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.spells.ModSpells;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.ArrayList;

public class TomeItem extends Item {
    public ArrayList<Identifier> spellList = new ArrayList<>();
    public Identifier selectedSpell = null;
    public int chargeTime = 0; // in ticks
    private PlayerEntity caster;

    public TomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        caster = user;
        ItemStack itemStack = user.getStackInHand(hand);

        Spell spell = (Spell) ModSpells.spellRegistry.get(selectedSpell);
        chargeTime = spell.chargeTimeTicks;

        if(chargeTime > 0) {
            user.setCurrentHand(hand);
            // actually casting the spell happens later in finishUsing
            return TypedActionResult.fail(itemStack);
        } else {
            // no charge up time, so cast it right away
            this.castSelectedSpell(world);
            return TypedActionResult.success(itemStack);
        }
//        if(hand == Hand.MAIN_HAND) {
//
//        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // at this point they have 'charged' the spell long enough and it auto-casts
        this.castSelectedSpell(world);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return chargeTime; // returns in ticks
    }

    public void switchSpell(int direction) {
        int currentIndex = spellList.indexOf(selectedSpell);
        if(direction == 1) {
            // we are going 'forward', increasing index
            if(currentIndex == spellList.size() - 1) {
                // we are at the end, flip to beginning
                selectedSpell = spellList.get(0);
            } else {
                selectedSpell = spellList.get(currentIndex + 1);
            }
        } else {
            // we are going 'backwards', decreasing index
            if(currentIndex == 0) {
                // we are at the beginning, flip to end
                selectedSpell = spellList.get(spellList.size() - 1);
            } else {
                selectedSpell = spellList.get(currentIndex - 1);
            }
        }

        Spell spell = (Spell) ModSpells.spellRegistry.get(selectedSpell);
        chargeTime = spell.chargeTimeTicks;
        MinecraftClient.getInstance().player.sendMessage(Text.literal("Switched to spell " + selectedSpell.toString()), false);
    }

    private void castSelectedSpell(World world) {
        // this part happens on the server for obvious security concerns
        Spell spell = (Spell) ModSpells.spellRegistry.get(selectedSpell);
        ToPMod.LOGGER.info("Trying to cast spell = " + selectedSpell.toString());
        if (!world.isClient) {
            ToPMod.LOGGER.info("Trying to cast spell on server = " + selectedSpell.toString());
            spell.castSpell(world, this.caster, Hand.MAIN_HAND, this);
        }

//        user.incrementStat(Stats.USED.getOrCreateStat(this)); // TODO figure out wtf this is
    }

//    private void spawnParticles(int amount) {
//        int i = this.getColor();
//        if (i == -1 || amount <= 0) {
//            return;
//        }
//        double d = (double)(i >> 16 & 0xFF) / 255.0;
//        double e = (double)(i >> 8 & 0xFF) / 255.0;
//        double f = (double)(i >> 0 & 0xFF) / 255.0;
//        for (int j = 0; j < amount; ++j) {
//            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
//        }
//    }

//    public int getColor() {
//        return this.dataTracker.get(COLOR);
//    }

//    private void setColor(int color) {
//        this.colorSet = true;
//        this.dataTracker.set(COLOR, color);
//    }
}
