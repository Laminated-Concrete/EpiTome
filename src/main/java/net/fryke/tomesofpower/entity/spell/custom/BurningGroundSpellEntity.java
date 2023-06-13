package net.fryke.tomesofpower.entity.spell.custom;

import net.fryke.tomesofpower.particles.ModParticles;
import net.fryke.tomesofpower.spells.types.Spell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BurningGroundSpellEntity extends CustomSpellEntity {
    private double radius = 5;
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
            List<LivingEntity> targetEntities = this.getEntitiesInRadius(world, getPos(), radius);

            // TODO this works, but it's totally invisible. we need to particle it up
            // TODO do we want to slow entities down as well?
            targetEntities.forEach((LivingEntity entity) -> {
                entity.setOnFireFor(1);
            });

            if(this.lifetimeTicks == -1 && this.remainingLifetimeTicks == -1) {
                this.kill();
            }
        } else {
            Vec3d centerPoint = getBoundingBox().getCenter();
            Vec3d pos = getRandomPointOnCircle(new Vec3d(centerPoint.getX(), getY(), centerPoint.getZ()), radius);

            world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
            world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
            world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
            world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
        }

//        if(tickCounter % 3 == 0) {
//        for (int i = 0; i < 1; i++) {

//        Vec3d centerPoint = getBoundingBox().getCenter();
//        Vec3d pos = getRandomPointOnCircle(new Vec3d(centerPoint.getX(), getY(), centerPoint.getZ()), radius);
////        if(tickCounter % )
//
//        world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
//        world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
//        world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
//        world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
//
//        MinecraftClient.getInstance().
//            world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
//        });


//
//        MinecraftClient.getInstance().execute(() -> {
//            world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
//        });
//
//        MinecraftClient.getInstance().execute(() -> {
//            world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
//        });



//        world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
//        world.addParticle(ParticleTypes.FLAME, pos.getX(), pos.getY() + 1, pos.getZ() + (2 * (centerPoint.getZ() - pos.getZ())), 0, 0.05, 0);
//        world.addParticle(ParticleTypes.FLAME, pos.getX() + (2 * (centerPoint.getX() - pos.getX())), pos.getY() + 1, pos.getZ(), 0, 0.05, 0);
//        }
//        }

//        if(tickCounter % 2 == 0) {
//            MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0.2, 0);
//        }
    }

    public Vec3d getRandomPointOnCircle(Vec3d centerPoint, double radius) {
        double randomNumber = (double) ((System.currentTimeMillis() * 2654435761L) & 0xFFFFFFFFL) / 0xFFFFFFFFL;
        double angle = randomNumber * 2 * Math.PI;
        double randomRadius = randomNumber * radius;

        double xOffset = Math.cos(angle) * randomRadius;
        double zOffset = Math.sin(angle) * randomRadius;

        double x = centerPoint.getX() + xOffset;
        double y = centerPoint.getY();
        double z = centerPoint.getZ() + zOffset;

        return new Vec3d(x, y, z);
    }


    public static List<LivingEntity> getEntitiesInRadius(World world, Vec3d center, double radius) {
        double squaredRadius = radius * radius;
        Box searchBox = new Box(center.add(-radius, -radius, -radius),
                center.add(radius, radius, radius));
        return world.getEntitiesByClass(LivingEntity.class, searchBox, entity -> entity.squaredDistanceTo(center) <= squaredRadius);
    }
}
