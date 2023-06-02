package net.fryke.tomesofpower.entity.spell.projectile;

import net.fryke.tomesofpower.entity.spell.projectile.ProjectileSpellEntity;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
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
        if (!this.world.isClient) { // if we are on the server
//            this.world.sendEntityStatus(this, (byte)3); // particle?
            // TODO lets make a little smoke particle on entity death or something?
            this.kill(); // kills the projectile
        }
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
}
