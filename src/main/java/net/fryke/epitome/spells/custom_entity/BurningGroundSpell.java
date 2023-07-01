package net.fryke.epitome.spells.custom_entity;

import net.fryke.epitome.client.sounds.SpellSoundEffect;
import net.fryke.epitome.entity.ModEntities;
import net.fryke.epitome.entity.spell.custom.BurningGroundSpellEntity;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.CustomEntitySpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BurningGroundSpell extends CustomEntitySpell {
    public BurningGroundSpell() {
        super();
        SPELL_ID = SpellIdentifiers.BURNING_GROUND_SPELL_ID;
        cooldownLengthTicks = 200; // 10s
        spellRange = 10;
        chargeTimeTicks = 40; // 2s
        lifetimeTicks = 100; // 5s
        on_cast_sound = SoundEvents.ENTITY_GHAST_SHOOT;
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = caster.raycast(spellRange, 0.0F, false);
        if(hitResult.getType() == HitResult.Type.MISS) { return; }
        if(hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Vec3d targetPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

            BurningGroundSpellEntity spellEntity = new BurningGroundSpellEntity(ModEntities.BURNING_GROUND_SPELL_ENTITY_TYPE, world, caster, this);
            spellEntity.setLifetimeTicks(lifetimeTicks);
            spellEntity.setPosition(targetPos);
            world.spawnEntity(spellEntity);

            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
