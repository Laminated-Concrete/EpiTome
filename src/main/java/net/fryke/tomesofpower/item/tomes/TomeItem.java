package net.fryke.tomesofpower.item.tomes;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.client.SpellPageAnimatable;
import net.fryke.tomesofpower.client.render.TomeRenderer;
import net.fryke.tomesofpower.spells.ModSpells;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TomeItem extends Item implements GeoItem {
    public Identifier tomeId;

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private SpellPageAnimatable spellPageAnimatable;

    public ArrayList<Identifier> spellList = new ArrayList<>();
    public Identifier selectedSpell = null;
    public int chargeTime = 0; // in ticks
    private PlayerEntity caster;

    public TomeItem(Settings settings) {
        super(settings);
        spellPageAnimatable = new SpellPageAnimatable(this);
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
//        ToPMod.LOGGER.info("Trying to cast spell = " + selectedSpell.toString());
        if (!world.isClient) {
//            ToPMod.LOGGER.info("Trying to cast spell on server = " + selectedSpell.toString());
            spell.castSpell(world, this.caster, Hand.MAIN_HAND, this);
        } else {
            spell.castSpellClient(world, this.caster, Hand.MAIN_HAND, this);
        }

//        user.incrementStat(Stats.USED.getOrCreateStat(this)); // TODO figure out wtf this is
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final TomeRenderer renderer = new TomeRenderer(spellPageAnimatable);

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.openBook", Animation.LoopType.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
