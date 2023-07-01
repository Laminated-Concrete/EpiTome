package net.fryke.epitome.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fryke.epitome.entity.spell.projectile.ProjectileSpellEntity;
import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.packets.SwitchSpellPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class ClientConnectionInitHandler implements ClientPlayConnectionEvents.Init{
    @Override
    public void onPlayInit(ClientPlayNetworkHandler handler, MinecraftClient client) {
        ClientPlayNetworking.registerGlobalReceiver(SwitchSpellPacket.TYPE, (packet, player, responseSender) -> {
            ModLogger.log("us player thing = " + player.getName());
            PlayerEntity targetPlayer = client.world.getPlayerByUuid(packet.playerUuid());
            ModLogger.log("targetPlayer thing = " + targetPlayer.getName());
            ModLogger.log("packet.selectedSpellId().toString() thing = " + packet.selectedSpellId().toString());

            ItemStack targetStack = targetPlayer.getEquippedStack(packet.equipmentSlot());
            ModLogger.log("targetStack thing = " + targetStack);
            NbtCompound nbt = targetStack.getNbt();//new NbtCompound();
            nbt.putString("epitome.selectedSpell", packet.selectedSpellId().toString());
            targetStack.setNbt(nbt);
        });



        ClientPlayNetworking.registerReceiver(ProjectileSpellEntity.SPAWN_SPELL_PACKET_ID, (msgClient, msgHandler, buf, responseSender) -> {
            // here we read the values in the order we added them to the packet. ORDER MATTERS
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            int entityId = buf.readInt();
            UUID entityUUID = buf.readUuid();
            Identifier spellEntityIdentifier = buf.readIdentifier();
            int lifetimeTicks = buf.readInt();
            RegistryKey<World> key = buf.readRegistryKey(RegistryKeys.WORLD);

            // here we need to execute the spawning code on the main thread. is this how?
            client.executeTask(() -> {
                if(spellEntityIdentifier != null) {
                    EntityType<?> spellEntity = Registries.ENTITY_TYPE.get(spellEntityIdentifier);
                    ProjectileSpellEntity entity = (ProjectileSpellEntity) spellEntity.create(MinecraftClient.getInstance().world);
                    assert entity != null;
                    entity.setSpellData(x, y, z, entityId, entityUUID, lifetimeTicks);

                    // we use a custom client-only constructor here
                    assert MinecraftClient.getInstance().world != null;
                    MinecraftClient.getInstance().world.addEntity(entityId, entity); // addEntity vs spawnEntity?
                }
            });
        });
    }
}
