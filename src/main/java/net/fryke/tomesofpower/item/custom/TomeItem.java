package net.fryke.tomesofpower.item.custom;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.ToPModClient;
import net.fryke.tomesofpower.spells.types.ProjectileSpellEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.ArrayList;

public class TomeItem extends Item {
    public ArrayList<Identifier> spellList = new ArrayList<Identifier>();
    public Identifier selectedSpell = null;

    public TomeItem(Settings settings) {
        super(settings);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ToPModClient.keyBinding.wasPressed()) {
                ItemStack mainHandStack = client.player.getInventory().getMainHandStack();
                if(mainHandStack.getItem().equals(this)) {
                    client.player.sendMessage(Text.literal("Key was pressed while holding item"), false);

                    int direction = 1;
                    int currentIndex = spellList.indexOf(selectedSpell);
                    if(direction == 1) {
                        // we are going 'forward', increasing index
                        if(currentIndex == spellList.size() - 1) {
                            // we are at the end, flip to beginning
                            selectedSpell = spellList.get(0);
                        } else {
                            selectedSpell = spellList.get(currentIndex + 1);
                        }
                    } else {
                        // we are going 'backwards', decreasing index
                        if(currentIndex == 0) {
                            // we are at the beginning, flip to end
                            selectedSpell = spellList.get(spellList.size() - 1);
                        } else {
                            selectedSpell = spellList.get(currentIndex - 1);
                        }
                    }
                    client.player.sendMessage(Text.literal("Switched to spell " + selectedSpell.toString()), false);
                } else {
                    client.player.sendMessage(Text.literal("Not holding item"), false);
                }
            }
        });

        spellList.add(new Identifier(ToPMod.MOD_ID, "spell_entity"));
        spellList.add(new Identifier(ToPMod.MOD_ID, "testing_projectile"));
        spellList.add(new Identifier(ToPMod.MOD_ID, "testing_spell_3"));
        selectedSpell = spellList.get(0);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(hand == Hand.MAIN_HAND) {
            ToPMod.LOGGER.info("trying to play sound");
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

            // this part happens on the server for obvious security concerns
            if (!world.isClient) {
                user.sendMessage(Text.literal("Trying to cast spell = " + selectedSpell.toString()));
                ToPMod.LOGGER.info("PRINTING THE THING");

                if(true) { // TODO figure this out. need to check if we are dealing with an 'projectile_entity' type spell
                    // If we are dealing with an Entity type spell
                    // First we need to get the projectile_entity type from the Registry
                    EntityType<?> spellEntityType = Registries.ENTITY_TYPE.get(selectedSpell);

                    // Then we create the projectile_entity
                    ProjectileSpellEntity spellEntity = (ProjectileSpellEntity) spellEntityType.create(world);
                    spellEntity.setPos(user.getX(), user.getY(), user.getZ()); // TODO how to get head position instead of feet position?
                    spellEntity.setOwner(user);

                    if(true) { // TODO figure out this check or move velocity init to the spell projectile class?
                        // If we are dealing with a projectile type projectile_entity, then we need to set its velocity?
                        spellEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 0F);
                    }
                    world.spawnEntity(spellEntity); // spawns projectile_entity
                } else if(false) { // TODO figure this check out. need to check if we are dealing with an 'interaction' type spell

                } else if(false) { // TODO figure this check out. need to check if we are dealing with a 'self' type spell

                }

            }

            user.incrementStat(Stats.USED.getOrCreateStat(this)); // TODO figure out wtf this is

//            if(!user.getAbilities().creativeMode) {
//                itemStack.decrement(1);
//
            if(ToPModClient.keyBinding.isPressed()) {
                user.sendMessage(Text.literal("Key is being pressed!"), false);
            }



            user.getItemCooldownManager().set(this, 10);
        }

        return super.use(world, user, hand);
    }

//    private void spawnParticles(int amount) {
//        int i = this.getColor();
//        if (i == -1 || amount <= 0) {
//            return;
//        }
//        double d = (double)(i >> 16 & 0xFF) / 255.0;
//        double e = (double)(i >> 8 & 0xFF) / 255.0;
//        double f = (double)(i >> 0 & 0xFF) / 255.0;
//        for (int j = 0; j < amount; ++j) {
//            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
//        }
//    }

//    public int getColor() {
//        return this.dataTracker.get(COLOR);
//    }

//    private void setColor(int color) {
//        this.colorSet = true;
//        this.dataTracker.set(COLOR, color);
//    }
}
