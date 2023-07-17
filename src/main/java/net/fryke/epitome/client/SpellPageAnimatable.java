package net.fryke.epitome.client;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

/*
 * This is a special class designed specifically for allowing us to render the "Spell Page" part of tomes
 *  and animate it as we see fit. To pull that off, we needed an animatable separate from the TomeItem.
 */
public class SpellPageAnimatable implements SingletonGeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private AnimationController controller;
    private String animationName = "animation.model.openBook";
    private String spellTextureName;
    private ModelTransformationMode renderingContext = null;
//    public final PlayerEntity player;

    private enum ModelStates {
        NEW,
        IDLE,
        HAS_NEW_ANIM,
        PAGES_FLIP_LEFT,
        PAGES_FLIP_RIGHT,
    }
    private SpellPageAnimatable.ModelStates animationState = ModelStates.NEW;

    public SpellPageAnimatable() {
//        this.player = player;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return RenderProvider.super.getCustomRenderer();
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controller = new AnimationController<>(this, "testing_controller", 0, this::predicate);
        controllerRegistrar.add(controller);
    }

    public void setAnimation(String animationName) {
        this.animationName = animationName;
        this.animationState = ModelStates.HAS_NEW_ANIM;
    }

    public void setRenderingContext(ModelTransformationMode renderingContext) {
        this.renderingContext = renderingContext;
    }

    public ModelTransformationMode getRenderingContext() {
        return this.renderingContext;
    }

    public void setSpellTextureName(String spellTextureName) {
        this.spellTextureName = spellTextureName;
    }

    public String getSpellTextureName() {
        return spellTextureName;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(renderingContext == null || renderingContext == ModelTransformationMode.GROUND || renderingContext == ModelTransformationMode.GUI) {
            return PlayState.CONTINUE;
        }

        AnimationController controller = tAnimationState.getController();
        AnimationController.State currentState = controller.getAnimationState();
        if(currentState == AnimationController.State.RUNNING) {
            return PlayState.CONTINUE;
        }

//        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.openBook", Animation.LoopType.PLAY_ONCE));
//        return PlayState.CONTINUE;

        if(animationState == ModelStates.IDLE) {
            // do nothing atm
            return PlayState.CONTINUE;
        } else if(animationState == ModelStates.NEW) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.openBook", Animation.LoopType.HOLD_ON_LAST_FRAME));
            animationState = ModelStates.IDLE;
            return PlayState.CONTINUE;
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then(this.animationName, Animation.LoopType.HOLD_ON_LAST_FRAME));
            animationState = ModelStates.IDLE;
            return PlayState.CONTINUE;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return RenderUtils.getCurrentTick();
    }

//    @Override
//    public boolean isPerspectiveAware() {
//        return true;
//    }
}
