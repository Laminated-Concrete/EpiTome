package net.fryke.epitome.helpers;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class RandomnessHelper {
    private long seed;
    private long multiplier = 1664525L;;
    private long increment = 1013904223L;;
    private long modulus = (1L << 32);;

    public RandomnessHelper() {
        this.seed = System.currentTimeMillis();
    }

    public int nextInt() {
        seed = (multiplier * seed + increment) % modulus;
        return (int) seed;
    }

    public float nextFloat() {
        return (nextInt() & 0x7FFFFFFF) / (float) 0x7FFFFFFF;
    }

    public static Vec3d getRandomPointInBox(Box box, Direction targetFace) {
        Random random = Random.create();

        Vec3d point = null;
        if(targetFace == Direction.DOWN) {
            point = new Vec3d(
                    box.getMin(Direction.Axis.X) + (box.getMax(Direction.Axis.X) - box.getMin(Direction.Axis.X)) * random.nextDouble(),
                    box.getMin(Direction.Axis.Y),
                    box.getMin(Direction.Axis.Z) + (box.getMax(Direction.Axis.Z) - box.getMin(Direction.Axis.Z)) * random.nextDouble());
        } else if(targetFace == Direction.UP) {
            point = new Vec3d(
                    box.getMin(Direction.Axis.X) + (box.getMax(Direction.Axis.X) - box.getMin(Direction.Axis.X)) * random.nextDouble(),
                    box.getMax(Direction.Axis.Y),
                    box.getMin(Direction.Axis.Z) + (box.getMax(Direction.Axis.Z) - box.getMin(Direction.Axis.Z)) * random.nextDouble());
        }

        return point;
    }
}
