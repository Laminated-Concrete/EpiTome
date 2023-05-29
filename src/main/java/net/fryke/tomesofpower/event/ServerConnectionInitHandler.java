package net.fryke.tomesofpower.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fryke.tomesofpower.entity.spell.ProjectileSpellEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class ServerConnectionInitHandler implements ServerPlayConnectionEvents.Init {
    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
        ClientPlayNetworking.registerReceiver(ProjectileSpellEntity.SPAWN_SPELL_PACKET_ID, (client, msgHandler, buf, responseSender) -> {
            // here we read the values in the order we added them to the packet. ORDER MATTERS
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            int entityId = buf.readInt();
            UUID entityUUID = buf.readUuid();
            Identifier spellEntityIdentifier = buf.readIdentifier();
            int lifetimeTicks = buf.readInt();

            // here we need to execute the spawning code on the main thread. is this how?
            client.executeTask(() -> {
                if(spellEntityIdentifier != null) {
                    EntityType spellEntity = Registries.ENTITY_TYPE.get(spellEntityIdentifier);
                    ProjectileSpellEntity test = (ProjectileSpellEntity) spellEntity.create(MinecraftClient.getInstance().world);
                    test.setSpellData(x, y, z, entityId, entityUUID, lifetimeTicks);

                    // we use a custom client-only constructor here
                    MinecraftClient.getInstance().world.addEntity(entityId, test); // addEntity vs spawnEntity?
                }
            });
        });
    }
}
