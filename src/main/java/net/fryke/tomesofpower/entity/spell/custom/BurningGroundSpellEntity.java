package net.fryke.tomesofpower.entity.spell.custom;

import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BurningGroundSpellEntity extends CustomSpellEntity {
    public BurningGroundSpellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public BurningGroundSpellEntity(EntityType<?> type, World world, Entity caster, Spell spell) {
        super(type, world);
        this.spell = spell;
    }

    @Override
    public void tick() {
        super.tick();

        if(!world.isClient) {
            double radius = 5;
            List<LivingEntity> targetEntities = this.getEntitiesInRadius(world, getPos(), radius);

            // TODO this works, but it's totally invisible. we need to particle it up
            // TODO do we want to slow entities down as well?
            targetEntities.forEach((LivingEntity entity) -> {
                entity.setOnFireFor(1);
            });

            if(this.lifetimeTicks == -1 && this.remainingLifetimeTicks == -1) {
                this.kill();
            }
        }
    }

    public static List<LivingEntity> getEntitiesInRadius(World world, Vec3d center, double radius) {
        double squaredRadius = radius * radius;
        Box searchBox = new Box(center.add(-radius, -radius, -radius),
                center.add(radius, radius, radius));
        return world.getEntitiesByClass(LivingEntity.class, searchBox, entity -> entity.squaredDistanceTo(center) <= squaredRadius);
    }
}
