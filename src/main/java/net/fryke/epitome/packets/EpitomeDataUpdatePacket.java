package net.fryke.epitome.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record EpitomeDataUpdatePacket(NbtCompound epitomeData, UUID playerUuid) implements FabricPacket {
    public static final PacketType<EpitomeDataUpdatePacket> TYPE = PacketType.create(PacketIdentifiers.EPITOME_DATA_PACKET_ID, EpitomeDataUpdatePacket::new);

    public EpitomeDataUpdatePacket(PacketByteBuf buf) {
        this(buf.readNbt(), buf.readUuid());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeNbt(this.epitomeData);
        buf.writeUuid(this.playerUuid);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
