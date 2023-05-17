package net.fryke.tomesofpower.spells.projectile_entity;

import net.fryke.tomesofpower.spells.types.ProjectileSpellEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class EmberSpellEntity extends ProjectileSpellEntity {
    public EmberSpellEntity(EntityType<? extends ProjectileSpellEntity> entityType, World world) {
        super(entityType, world);
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
            this.kill(); // kills the projectile
        }
    }
}
