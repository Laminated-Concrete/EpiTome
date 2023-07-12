package net.fryke.epitome.item.tomes;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.ModSounds;
import net.fryke.epitome.client.SpellPageAnimatable;
import net.fryke.epitome.client.render.TomeRenderer;
import net.fryke.epitome.packets.SwitchSpellPacket;
import net.fryke.epitome.spells.ModSpells;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.Spell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.keyframe.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TomeItem extends Item implements GeoItem {
    public Identifier tomeId;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    protected final SpellPageAnimatable spellPageAnimatable;

    public final ArrayList<Identifier> spellList = new ArrayList<>();
    public Identifier selectedSpell = null;
    public int chargeTime = 0; // in ticks
    private PlayerEntity caster;

    private enum ModelStates {
        NEW,
        IDLE_OPEN,
        IDLE_CLOSED,
        PAGES_IDLE,
        PAGES_FLIP_LEFT,
        PAGES_FLIP_RIGHT,
    }
    private ModelStates bookState = ModelStates.NEW;
    private ModelStates pagesState = ModelStates.NEW;

//    private final TomeSoundEffect casting_loop_sound = new TomeSoundEffect(caster, ModSounds.CASTING_LOOP_EVENT, true);

    private final SoundEvent started_casting_sound = ModSounds.STARTED_CASTING_ACCENT_EVENT;
    private final SoundEvent finished_casting_sound_1 = ModSounds.FINISHED_CASTING_ACCENT_1_EVENT;
    private final SoundEvent finished_casting_sound_2 = ModSounds.FINISHED_CASTING_ACCENT_2_EVENT;

    public TomeItem(Settings settings) {
        super(settings);
        spellPageAnimatable = new SpellPageAnimatable(); // not sure if this is right
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        caster = user;
        ItemStack itemStack = user.getStackInHand(hand);

        Spell spell = (Spell) ModSpells.spellRegistry.get(selectedSpell);
        assert spell != null;
        chargeTime = spell.chargeTimeTicks;

        world.playSound(null, user.getBlockPos(), started_casting_sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if(chargeTime > 0) {
            user.setCurrentHand(hand);
            // actually casting the spell happens later in finishUsing
            return TypedActionResult.consume(itemStack);
        } else {
            // no charge uptime, so cast it right away
            this.castSelectedSpell(world, itemStack);
            return TypedActionResult.success(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
        float chargeProgress = this.getChargeProgress(useTicks);
        if(chargeProgress == 1.0F) {
            ModLogger.log("Spell is ready to cast");
        }

//        if(world.isClient) {
//            if(!MinecraftClient.getInstance().getSoundManager().isPlaying(casting_loop_sound)) {
//                MinecraftClient.getInstance().getSoundManager().play(casting_loop_sound);
//            }
//        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
        float chargeProgress = this.getChargeProgress(useTicks);

//        if(MinecraftClient.getInstance().getSoundManager().isPlaying(casting_loop_sound)) {
//            MinecraftClient.getInstance().getSoundManager().stop(casting_loop_sound);
//        }

        if (chargeProgress == 1.0F) {
            ModLogger.log("Finished charging, we want to cast to spell now");
            world.playSound(null, user.getBlockPos(), finished_casting_sound_1, SoundCategory.PLAYERS, 1.0f, 1.0f);
            world.playSound(null, user.getBlockPos(), finished_casting_sound_2, SoundCategory.PLAYERS, 1.0f, 1.0f);

            this.castSelectedSpell(world, stack);
        }
        ModLogger.log("On stopped using");
    }

    public float getChargeProgress(int useTicks) {
        float percentage = (float) useTicks / this.chargeTime;
        if (percentage > 1.0F) {
            percentage = 1.0F;
        }
        return percentage;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        // so the way this works is we set this to a really high number
        // when they actually stop letting go of the mouse button, we do a thing
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    public void switchSpell(ItemStack stack, int direction, EquipmentSlot equipmentSlot) {
        ModLogger.log("Switching spell in direction " + direction);
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

        Spell spell = ModSpells.spellRegistry.get(selectedSpell);
        assert spell != null;
        chargeTime = spell.chargeTimeTicks;
        assert MinecraftClient.getInstance().player != null;

        SwitchSpellPacket packet = new SwitchSpellPacket(selectedSpell, MinecraftClient.getInstance().player.getUuid(), equipmentSlot);
        ClientPlayNetworking.send(packet);

        if(direction == 1) {
            pagesState = ModelStates.PAGES_FLIP_LEFT;
        } else {
            pagesState = ModelStates.PAGES_FLIP_RIGHT;
        }
    }

    private void castSelectedSpell(World world, ItemStack stack) {
        Identifier targetSpell;
        if(stack.hasNbt()) {
            targetSpell = new Identifier(stack.getNbt().getString("epitome.selectedSpell"));
        } else {
            return;
//            targetSpell = selectedSpell;
        }

        Spell spell = ModSpells.spellRegistry.get(targetSpell);
        assert spell != null;

        if (!world.isClient) {
            spell.doCastSpell(world, this.caster, Hand.MAIN_HAND, this);
        } else {
            spell.doCastSpellClient(world, this.caster, Hand.MAIN_HAND, this);
        }

//        user.incrementStat(Stats.USED.getOrCreateStat(this)); // TODO figure out wtf this is
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = new ItemStack(this);
        NbtCompound nbt = new NbtCompound();
        nbt.putString("epitome.selectedSpell", SpellIdentifiers.EMBER_SPELL_ID.toString());
        itemStack.setNbt(nbt);
        return itemStack;
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
        AnimationController<TomeItem> bookController = new AnimationController<>(this, "book_controller", 0, this::bookPredicate);
        AnimationController<TomeItem> pagesController = new AnimationController<>(this, "pages_controller", 0, this::pagesPredicate);

        pagesController.setCustomInstructionKeyframeHandler(customInstructionKeyframeEvent -> {
            String instruction = customInstructionKeyframeEvent.getKeyframeData().getInstructions();
            ModLogger.log(instruction + " " + (instruction == "switchPageTexture;") + instruction.equals("switchPageTexture;"));
            if(instruction.equals("switchPageTexture;")) { // there is automatically a semicolon at the end of the instruction
                spellPageAnimatable.setSpellTextureName(selectedSpell.getPath());
            }
        });

        controllerRegistrar.add(bookController);
        controllerRegistrar.add(pagesController);
    }

    private <T extends GeoAnimatable> PlayState bookPredicate(AnimationState<T> tAnimationState) {
        AnimationController controller = tAnimationState.getController();
        AnimationController.State currentState = tAnimationState.getController().getAnimationState();

        ModelTransformationMode renderingContext = tAnimationState.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);
        spellPageAnimatable.setRenderingContext(renderingContext);
        if(renderingContext == ModelTransformationMode.GROUND || renderingContext == ModelTransformationMode.GUI) {
            return PlayState.CONTINUE;
        }

        if(currentState == AnimationController.State.RUNNING) {
            return PlayState.CONTINUE;
        }

        if(bookState == ModelStates.NEW) {
            ModLogger.log("NEW STATE TRIGGERED");
            // We are new and want to initialize the model
            controller.setAnimation(RawAnimation.begin().then("animation.model.openBook", Animation.LoopType.HOLD_ON_LAST_FRAME));
            spellPageAnimatable.setAnimation("animation.model.openBook");
            // after this animation we are in an IDLE_OPEN state
            bookState = ModelStates.IDLE_OPEN;
        }

        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState pagesPredicate(AnimationState<T> tAnimationState) {
        AnimationController controller = tAnimationState.getController();
        AnimationController.State currentState = controller.getAnimationState();

        ModelTransformationMode context = tAnimationState.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);
        if(context == ModelTransformationMode.GROUND || context == ModelTransformationMode.GUI) {
            return PlayState.CONTINUE;
        }

//        if(currentState == AnimationController.State.RUNNING) {
//            ModLogger.log("PAGES ANIMATION IS RUNNING, DO NOTHING");
//            return PlayState.CONTINUE;
//        }
        
        if(pagesState == ModelStates.PAGES_FLIP_LEFT) {
            ModLogger.log("PAGE FLIP LEFT STATE TRIGGERED");
            // We should start in the IDLE_OPEN state and end up there
            controller.setAnimation(RawAnimation.begin().then("animation.model.pagesFlipLeft", Animation.LoopType.PLAY_ONCE));
            spellPageAnimatable.setAnimation("animation.model.pagesFlipLeft");
            pagesState = ModelStates.PAGES_IDLE;

            if(currentState == AnimationController.State.RUNNING || currentState == AnimationController.State.STOPPED) {
                controller.forceAnimationReset();
            }
        } else if(pagesState == ModelStates.PAGES_FLIP_RIGHT) {
            ModLogger.log("PAGE FLIP RIGHT STATE TRIGGERED");
            // We should start in the IDLE_OPEN state and end up there
            controller.setAnimation(RawAnimation.begin().then("animation.model.pagesFlipRight", Animation.LoopType.PLAY_ONCE));
            spellPageAnimatable.setAnimation("animation.model.pagesFlipRight");
            pagesState = ModelStates.PAGES_IDLE;

            if(currentState == AnimationController.State.RUNNING || currentState == AnimationController.State.STOPPED) {
                controller.forceAnimationReset();
            }
        } else if(pagesState == ModelStates.PAGES_IDLE) {
//            controller.setAnimation(RawAnimation.begin().then("animation.model.pagesIdle", Animation.LoopType.LOOP));
        }
//
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }
}
