package net.fryke.epitome.entity.spell.projectile;

import net.fryke.epitome.spells.types.Spell;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class EmberSpellEntity extends ProjectileSpellEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public EmberSpellEntity(EntityType<? extends ProjectileSpellEntity> entityType, World world) {
        super(entityType, world);
    }

    public EmberSpellEntity(EntityType<? extends ProjectileSpellEntity> entityType, World world, float gravity, float dragScalar, Entity caster, Spell spell) {
        super(entityType, world);
        this.gravity = gravity;
        this.dragScalar = dragScalar;
        this.spell = spell;
        this.setOwner(caster);
        this.setPosition(caster.getX(), caster.getEyeY() - 0.1, caster.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        double minValue = -0.1;
        double maxValue = 0.1;

        double randomNumber = (double) ((System.currentTimeMillis() * 2654435761L) & 0xFFFFFFFFL) / 0xFFFFFFFFL;
        if(tickCounter % 3 == 0) {
            getWorld().addParticle(
                    ParticleTypes.FLAME,
                    getBoundingBox().getCenter().getX(), getBoundingBox().getCenter().getY(), getBoundingBox().getCenter().getZ(),
                    minValue + (maxValue - minValue) * randomNumber, 0.0, minValue + (maxValue - minValue) * Math.random()
            );
        }

        if(tickCounter % 5 == 0) {
            MinecraftClient.getInstance().particleManager.addParticle(
                    ParticleTypes.SMOKE,
                    getBoundingBox().getCenter().getX(), getBoundingBox().getCenter().getY(), getBoundingBox().getCenter().getZ(),
                    minValue + (maxValue - minValue) * randomNumber, 0.1, minValue + (maxValue - minValue) * Math.random()
            );
        }
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        // this spell is meant to do little direct damage, but set things on fire for a decent amount of time
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(this.getDamageSources().generic(), 2f); // deals damage
        entity.setOnFireFor(10); // number is in seconds
    }

    @Override
    public void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) { // if we are on the server
//            this.world.sendEntityStatus(this, (byte)3); // particle?
            // TODO lets make a little smoke particle on entity death or something?
            this.kill(); // kills the projectile

            if(hitResult instanceof BlockHitResult) {
                BlockState blockState = getWorld().getBlockState(((BlockHitResult) hitResult).getBlockPos());
                this.setBlockOnFire((BlockHitResult) hitResult, blockState, (PlayerEntity) this.getOwner(), this.getWorld());
            }
        }
    }

    @Override
    protected Box calculateBoundingBox() {
        double size = 0.25;
        Vec3d initialBoxCenter = getDimensions(getPose()).getBoxAt(getPos()).getCenter();
        return new Box(
                initialBoxCenter.getX() + (size/2),
                initialBoxCenter.getY() + (size/2),
                initialBoxCenter.getZ() + (size/2),
                initialBoxCenter.getX() - (size/2),
                initialBoxCenter.getY() - (size/2),
                initialBoxCenter.getZ() - (size/2));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        // not sure if we need this for entities that have no animations?
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        // idle animation spot?
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private void setBlockOnFire(BlockHitResult hitResult, BlockState blockState, PlayerEntity playerEntity, World world) {
        // ripped from flint and steel
        if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
            world.setBlockState(hitResult.getBlockPos(), blockState.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, hitResult.getBlockPos());
        }

        BlockPos blockPos2 = hitResult.getBlockPos().offset(hitResult.getSide());
        if (AbstractFireBlock.canPlaceAt(world, blockPos2, hitResult.getSide())) {
            BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
            world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, hitResult.getBlockPos());
        }
    }
}
