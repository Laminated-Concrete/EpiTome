package net.fryke.epitome.event;

import io.netty.channel.ChannelHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.lookup.block.ServerWorldCache;
import net.fryke.epitome.entity.spell.projectile.ProjectileSpellEntity;
import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.packets.SwitchSpellPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class ServerConnectionInitHandler implements ServerPlayConnectionEvents.Init {
    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
        ServerPlayNetworking.registerGlobalReceiver(SwitchSpellPacket.TYPE, (packet, player, responseSender) -> {
            ItemStack targetStack = player.getEquippedStack(packet.equipmentSlot());
            NbtCompound nbt = targetStack.getOrCreateNbt();//new NbtCompound();
            ModLogger.log(nbt.asString());
            nbt.putString("epitome.selectedSpell", packet.selectedSpellId().toString());
//            mainStack.setNbt(nbt);

            // Iterate over all players tracking a position in the world and send the packet to each player
            for (ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking(player)) {
                ServerPlayNetworking.send(serverPlayerEntity, new SwitchSpellPacket(packet.selectedSpellId(), player.getUuid(), packet.equipmentSlot()));
            }
        });

//        ClientPlayNetworking.registerReceiver(ProjectileSpellEntity.SPAWN_SPELL_PACKET_ID, (client, msgHandler, buf, responseSender) -> {
//            // here we read the values in the order we added them to the packet. ORDER MATTERS
//            double x = buf.readDouble();
//            double y = buf.readDouble();
//            double z = buf.readDouble();
//            int entityId = buf.readInt();
//            UUID entityUUID = buf.readUuid();
//            Identifier spellEntityIdentifier = buf.readIdentifier();
//            int lifetimeTicks = buf.readInt();
//            RegistryKey<World> key = buf.readRegistryKey(RegistryKeys.WORLD);
//
//            // here we need to execute the spawning code on the main thread. is this how?
//            client.executeTask(() -> {
//                if(spellEntityIdentifier != null) {
//                    EntityType<?> spellEntity = Registries.ENTITY_TYPE.get(spellEntityIdentifier);
//                    ProjectileSpellEntity entity = (ProjectileSpellEntity) spellEntity.create(server.getWorld(key));
//                    assert entity != null;
//                    entity.setSpellData(x, y, z, entityId, entityUUID, lifetimeTicks);
//
//                    // we use a custom client-only constructor here
//                    assert MinecraftClient.getInstance().world != null;
//                    MinecraftClient.getInstance().world.addEntity(entityId, entity); // addEntity vs spawnEntity?
//                }
//            });
//        });
    }
}
