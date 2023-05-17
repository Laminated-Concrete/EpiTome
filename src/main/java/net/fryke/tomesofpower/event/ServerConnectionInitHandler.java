package net.fryke.tomesofpower.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fryke.tomesofpower.spells.types.ProjectileSpellEntity;
import net.fryke.tomesofpower.entity.custom.TestingProjectileEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.util.UUID;

public class ServerConnectionInitHandler implements ServerPlayConnectionEvents.Init {
    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
        ClientPlayNetworking.registerReceiver(TestingProjectileEntity.SPAWN_PACKET, (client, msgHandler, buf, responseSender) -> {
            // here we read the values in the order we added them to the packet. ORDER MATTERS
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            int entityId = buf.readInt();
            UUID entityUUID = buf.readUuid();

            // here we need to execute the spawning code on the main thread. is this how?
            client.executeTask(() -> {
                // we use a custom client-only constructor here
                TestingProjectileEntity proj = new TestingProjectileEntity(MinecraftClient.getInstance().world, x, y, z, entityId, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityId, proj); // TODO addEntity vs spawnEntity?
            });
        });

        ClientPlayNetworking.registerReceiver(ProjectileSpellEntity.SPAWN_SPELL_PACKET_ID, (client, msgHandler, buf, responseSender) -> {
            // here we read the values in the order we added them to the packet. ORDER MATTERS
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            int entityId = buf.readInt();
            UUID entityUUID = buf.readUuid();

            // here we need to execute the spawning code on the main thread. is this how?
            client.executeTask(() -> {
                // we use a custom client-only constructor here
                ProjectileSpellEntity spell = new ProjectileSpellEntity(MinecraftClient.getInstance().world, x, y, z, entityId, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityId, spell); // addEntity vs spawnEntity?
            });
        });
    }
}
