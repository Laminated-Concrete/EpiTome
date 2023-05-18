package net.fryke.tomesofpower.entity.spell;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class ProjectileSpellEntity extends ProjectileEntity {
    public static final Identifier ENTITY_PROJECTILE_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "projectile_entity_spell_type");
    public static final Identifier SPAWN_SPELL_PACKET_ID = new Identifier(ToPMod.MOD_ID, "spawn_projectile_spell");
    protected float gravity = 0f;
    protected float dragScalar = 0.99f;

    public ProjectileSpellEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public ProjectileSpellEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(ModEntities.SPELL_ENTITY_TYPE, world);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }

    @Override
    protected void initDataTracker() {

    }

//    @Override
//    public boolean shouldRender(double distance) {
//        double d = this.getBoundingBox().getAverageSideLength() * 4.0;
//        if (Double.isNaN(d)) {
//            d = 4.0;
//        }
//        return distance < (d *= 64.0) * d;
//    }

    @Override
    public void tick() {
        double appliedDrag;
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                bl = true;
            } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
                }
                bl = true;
            }
        }
        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onCollision(hitResult);
        }
        this.checkBlockCollision();
        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                float g = 0.25f;
                this.world.addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
            }
            appliedDrag = (double)this.getDrag() - 0.2;
        } else {
            appliedDrag = this.getDrag();
        }
        this.setVelocity(vec3d.multiply(appliedDrag));
        if (!this.hasNoGravity()) {
            Vec3d vec3d2 = this.getVelocity();
            this.setVelocity(vec3d2.x, vec3d2.y - (double)this.getGravity(), vec3d2.z);
        }
        this.setPosition(d, e, f);
    }

    public float getGravity() {
        return gravity;
    }

    public float getDrag() {
        return dragScalar;
    }

    /**
     * Is run on the server. Prepares a packet to send from server to client to let the client know an projectile_entity spawned
     * @return Packet<ClientPlayPacketListener>
     */
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        // fyi this is run on the server. prepares a packet to send from server to client to let the client know an projectile_entity spawned

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer()); // in theory should dynamically resize itself based on what is put in

        // projectile_entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // projectile_entity id and uuid
        packet.writeInt(getId());
        packet.writeUuid(getUuid()); // not sure if we need this

        // remember this is just creating the packet. It is sent automatically after this point
        return ServerPlayNetworking.createS2CPacket(SPAWN_SPELL_PACKET_ID, packet);
    }
}
