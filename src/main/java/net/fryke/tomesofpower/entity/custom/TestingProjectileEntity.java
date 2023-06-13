package net.fryke.tomesofpower.entity.custom;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.UUID;

public class TestingProjectileEntity extends ThrownEntity {
    public static final Identifier SPAWN_PACKET = new Identifier(ToPMod.MOD_ID, "spawn_testing_projectile_entity");

    public TestingProjectileEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public TestingProjectileEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(ModEntities.TESTING_PROJECTILE_ENTITY_ENTITY_TYPE, world);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }

    @Override
    protected void initDataTracker() {
        // this is for tracking data from server to client
        // i.e. wither skulls can be charged or not charged,
        //  and they are rendered differently based on that state
        //  so the client needs to know it

        //  this is kinda like NBT where you'd sync that from server to client in the block projectile_entity
        //  this is how standard entities do it

    }

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
        return ServerPlayNetworking.createS2CPacket(SPAWN_PACKET, packet);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(this.getDamageSources().generic(), 2f); // deals damage
    }

    @Override
    public void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) { // checks if the world is client
//            this.world.sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

    }
}
