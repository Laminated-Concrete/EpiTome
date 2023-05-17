package net.fryke.tomesofpower.spells.types;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class ProjectileSpellEntity extends ProjectileEntity implements SpellInterface {
    public static final Identifier ENTITY_PROJECTILE_SPELL_TYPE = new Identifier(ToPMod.MOD_ID, "projectile_entity_spell_type");
    public static final Identifier SPAWN_SPELL_PACKET_ID = new Identifier(ToPMod.MOD_ID, "spawn_projectile_spell");

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
    public Identifier getSpellType() {
        return ENTITY_PROJECTILE_SPELL_TYPE;
    }

    @Override
    protected void initDataTracker() {

    }

    /**
     * Is run on the server. Prepares a packet to send from server to client to let the client know an projectile_entity spawned
     * @return
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
