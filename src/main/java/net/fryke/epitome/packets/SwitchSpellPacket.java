package net.fryke.epitome.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record SwitchSpellPacket(Identifier selectedSpellId, UUID playerUuid, EquipmentSlot equipmentSlot) implements FabricPacket {
    public static final PacketType<SwitchSpellPacket> TYPE = PacketType.create(PacketIdentifiers.SWITCH_SPELL_PACKET_ID, SwitchSpellPacket::new);

    public SwitchSpellPacket(PacketByteBuf buf) {
        this(buf.readIdentifier(), buf.readUuid(), buf.readEnumConstant(EquipmentSlot.class));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(this.selectedSpellId);
        buf.writeUuid(this.playerUuid);
        buf.writeEnumConstant(this.equipmentSlot);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}