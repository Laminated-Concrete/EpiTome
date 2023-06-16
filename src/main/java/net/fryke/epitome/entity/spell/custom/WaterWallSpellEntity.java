package net.fryke.epitome.entity.spell.custom;

import net.fryke.epitome.helpers.RandomnessHelper;
import net.fryke.epitome.particles.ModParticles;
import net.fryke.epitome.spells.types.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class WaterWallSpellEntity extends CustomSpellEntity {
    public WaterWallSpellEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public WaterWallSpellEntity(EntityType<?> type, World world, Entity caster, Spell spell) {
        super(type, world);
        this.spell = spell;
    }

    @Override
    public void tick() {
        super.tick();
        this.setBoundingBox(this.calculateBoundingBox());

        if(getWorld().isClient) {
            for (int i = 0; i < 10; i++) {
                Vec3d randomPoint = RandomnessHelper.getRandomPointInBox(getBoundingBox(), Direction.DOWN);
                getWorld().addParticle(ModParticles.FALLING_UP_WATER, randomPoint.getX(), randomPoint.getY(), randomPoint.getZ(), 0, 0.2, 0);
            }
        } else {
            List<Entity> entities = getWorld().getOtherEntities(this, getBoundingBox(), (entity) -> !entity.isSpectator());
            entities.forEach((entity) -> {
                Vec3d initialVelocity = entity.getVelocity();
                double xVelocity = -initialVelocity.getX();
                double zVelocity = -initialVelocity.getZ();

                Direction direction = Direction.fromRotation(getYaw());
                if(direction == Direction.NORTH || direction == Direction.SOUTH) {
                    if(entity.getZ() < getZ()) {
                        zVelocity = -0.5;
                    } else {
                        zVelocity = 0.5;
                    }
                } else if(direction == Direction.WEST || direction == Direction.EAST) {
                    if(entity.getX() < getX()) {
                        xVelocity = -0.5;
                    } else {
                        xVelocity = 0.5;
                    }
                }

                entity.setVelocity(xVelocity, 0.5, zVelocity);
                entity.velocityModified = true;
            });

            if(this.lifetimeTicks == -1 && this.remainingLifetimeTicks == -1) {
                this.kill();
            }
        }
    }

    @Override
    protected Box calculateBoundingBox() {
        double width = 5;
        double thickness = 1;
        double height = 4;
        Vec3d initialBoxCenter = getDimensions(getPose()).getBoxAt(getPos()).getCenter();

        Direction direction = Direction.fromRotation(getYaw());
        Vec3d firstCorner = null;
        Vec3d secondCorner = null;
        if(direction == Direction.NORTH || direction == Direction.SOUTH) {
            firstCorner = new Vec3d(initialBoxCenter.getX() + (width / 2), getY() - 1, initialBoxCenter.getZ() + (thickness / 2));
            secondCorner = new Vec3d(initialBoxCenter.getX() - (width / 2), getY() - 1 + height, initialBoxCenter.getZ() - (thickness / 2));
        } else if(direction == Direction.WEST || direction == Direction.EAST) {
            firstCorner = new Vec3d(initialBoxCenter.getX() + (thickness / 2), getY() - 1, initialBoxCenter.getZ() + (width / 2));
            secondCorner = new Vec3d(initialBoxCenter.getX() - (thickness / 2), getY() - 1 + height, initialBoxCenter.getZ() - (width / 2));
        } else {

        }

        return new Box(firstCorner, secondCorner);
    }


}
