package net.fryke.testingmod.entity.custom;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryke.testingmod.TestingMod;
import net.fryke.testingmod.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class SpellEntity extends Entity {
    public static final Identifier SPAWN_SPELL_PACKET_ID = new Identifier(TestingMod.MOD_ID, "spawn_spell");

    public SpellEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public SpellEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(ModEntities.SPELL_ENTITY_TYPE, world);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    /**
     * Is run on the server. Prepares a packet to send from server to client to let the client know an entity spawned
     * @return
     */
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        // fyi this is run on the server. prepares a packet to send from server to client to let the client know an entity spawned

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer()); // in theory should dynamically resize itself based on what is put in

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id and uuid
        packet.writeInt(getId());
        packet.writeUuid(getUuid()); // not sure if we need this

        // remember this is just creating the packet. It is sent automatically after this point
        return ServerPlayNetworking.createS2CPacket(SPAWN_SPELL_PACKET_ID, packet);
    }
}
