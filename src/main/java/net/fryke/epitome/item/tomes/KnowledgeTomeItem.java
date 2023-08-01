package net.fryke.epitome.item.tomes;

import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.client.render.KnowledgeTomeRenderer;
import net.fryke.epitome.item.ModItems;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.data.client.Model;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
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
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class KnowledgeTomeItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    private enum ModelStates {
        OPEN,
        CLOSED,
    }
    private ModelStates bookState = ModelStates.CLOSED;

    public KnowledgeTomeItem(Settings settings) {
        super(settings);
    }

    public static boolean isBookOpen() {
        Identifier tomeId = Registries.ITEM.getKey(ModItems.KNOWLEDGE_TOME).get().getValue();
        return tomeId.equals(PatchouliAPI.get().getOpenBookGui());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Identifier tomeId = Registries.ITEM.getKey(ModItems.KNOWLEDGE_TOME).get().getValue();

        if(world.isClient) {
            PatchouliAPI.get().openBookGUI(tomeId);
            return TypedActionResult.success(itemStack, false);
        } else {
            if(user instanceof ServerPlayerEntity serverPlayer) {
                PatchouliAPI.get().openBookGUI(serverPlayer, tomeId);
                // play a book opening sound effect?
                return TypedActionResult.success(itemStack, false);
            } else {
                return TypedActionResult.success(itemStack, false);
            }
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final KnowledgeTomeRenderer renderer = new KnowledgeTomeRenderer();

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
        AnimationController<KnowledgeTomeItem> bookController = new AnimationController<>(this, "book_controller", 0, this::bookPredicate);
        AnimationController<KnowledgeTomeItem> runeController = new AnimationController<>(this, "pages_controller", 0, this::runePredicate);

        controllerRegistrar.add(bookController);
        controllerRegistrar.add(runeController);
    }

    private <T extends GeoAnimatable> PlayState runePredicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.idle"));
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState bookPredicate(AnimationState<T> tAnimationState) {
        ModelTransformationMode context = tAnimationState.getData(DataTickets.ITEM_RENDER_PERSPECTIVE);
        if(context == ModelTransformationMode.GROUND || context == ModelTransformationMode.GUI) {
            return PlayState.CONTINUE;
        }

        AnimationController controller = tAnimationState.getController();
        if(isBookOpen()) {
            controller.setAnimation(RawAnimation.begin().then("animation.model.openBook", Animation.LoopType.HOLD_ON_LAST_FRAME));
            bookState = ModelStates.OPEN;
        } else {
            if(bookState == ModelStates.OPEN) {
                controller.setAnimation(RawAnimation.begin().then("animation.model.closeBook", Animation.LoopType.HOLD_ON_LAST_FRAME));
                bookState = ModelStates.CLOSED;
            }
        }
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
