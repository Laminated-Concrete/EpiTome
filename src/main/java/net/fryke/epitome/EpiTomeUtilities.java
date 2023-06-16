package net.fryke.epitome;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class EpiTomeUtilities {
    public static EntityHitResult configurableEntityRaycast(float tickDelta, double distance, float targetingMargin) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity cameraEntity = client.getCameraEntity();
        if (cameraEntity == null || client.world == null) {
            return null;
        }
//        HitResult targetEntity = cameraEntity.raycast(distance, tickDelta, false);
        Vec3d cameraPosVec = cameraEntity.getCameraPosVec(tickDelta);
        double distanceSquared = distance * distance;

//        if (targetEntity != null) {
//            distanceSquared = targetEntity.getPos().squaredDistanceTo(cameraPosVec);
//        }
        Vec3d cameraRotationVec = cameraEntity.getRotationVec(1.0f);
        Vec3d endPointVec = cameraPosVec.add(cameraRotationVec.x * distance, cameraRotationVec.y * distance, cameraRotationVec.z * distance);

        Box box = cameraEntity.getBoundingBox().stretch(cameraRotationVec.multiply(distance)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = configurableRaycast(cameraEntity, cameraPosVec, endPointVec, box, entity -> !entity.isSpectator(), distanceSquared, targetingMargin);
//        if (entityHitResult != null) {
//            Vec3d vec3d4 = entityHitResult.getPos();
//            double g = cameraPosVec.squaredDistanceTo(vec3d4);
//            if (g < distanceSquared || targetEntity == null) {
//                targetEntity = entityHitResult;
//            }
//        }

        HitResult targetEntity = entityHitResult;

        if(!(targetEntity instanceof EntityHitResult)) {
            return null;
        }
        return (EntityHitResult) targetEntity;
    }

    public static EntityHitResult configurableRaycast(Entity entity, Vec3d min, Vec3d max, Box box, Predicate<Entity> predicate, double distance, float targetingMargin) {
        World world = entity.world;
        double localDistance = distance;
        Entity finalEntity = null;
        Vec3d vec3d = null;

        // for each entity found via getOtherEntities in the bounding box
        for (Entity otherEntity : world.getOtherEntities(entity, box, predicate)) {
            Vec3d vec3d2;
            double f;
            // so here we get the bounding box of the current interation entity and expand it based on its targeting margin
//            Box otherEntityBoundingBox = otherEntity.getBoundingBox().expand(otherEntity.getTargetingMargin());
            // but we want that targeting margin to be configurable, so we do it via a param instead
            Box otherEntityBoundingBox = otherEntity.getBoundingBox().expand(targetingMargin);
            Optional<Vec3d> optional = otherEntityBoundingBox.raycast(min, max);
            if (otherEntityBoundingBox.contains(min)) {
                if (!(localDistance >= 0.0)) continue;
                finalEntity = otherEntity;
                vec3d = optional.orElse(min);
                localDistance = 0.0;
                continue;
            }
            if (!optional.isPresent() || !((f = min.squaredDistanceTo(vec3d2 = optional.get())) < localDistance) && localDistance != 0.0) continue;
            if (otherEntity.getRootVehicle() == entity.getRootVehicle()) {
                if (localDistance != 0.0) continue;
                finalEntity = otherEntity;
                vec3d = vec3d2;
                continue;
            }
            finalEntity = otherEntity;
            vec3d = vec3d2;
            localDistance = f;
        }
        if (finalEntity == null) {
            return null;
        }

        return new EntityHitResult(finalEntity, vec3d);
    }
}
