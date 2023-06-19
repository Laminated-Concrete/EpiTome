package net.fryke.epitome.spells.custom_entity;

import net.fryke.epitome.entity.ModEntities;
import net.fryke.epitome.entity.spell.custom.WaterWallSpellEntity;
import net.fryke.epitome.item.tomes.TomeItem;
import net.fryke.epitome.spells.SpellIdentifiers;
import net.fryke.epitome.spells.types.CustomEntitySpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WaterWallSpell extends CustomEntitySpell {
    public WaterWallSpell() {
        SPELL_ID = SpellIdentifiers.WATER_WALL_SPELL_ID;
        cooldownLengthTicks = 200; // 10s
        spellRange = 10;
        chargeTimeTicks = 40; // 2s
        lifetimeTicks = 100; // 5s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = caster.raycast(spellRange, 0.0F, false);
        if(hitResult.getType() == HitResult.Type.MISS) { return; }
        if(hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Vec3d targetPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5);

            WaterWallSpellEntity spellEntity = new WaterWallSpellEntity(ModEntities.WATER_WALL_SPELL_ENTITY_TYPE, world, caster, this);
            spellEntity.setLifetimeTicks(lifetimeTicks);
            spellEntity.setPosition(targetPos);
            spellEntity.setYaw(caster.getYaw());
            world.spawnEntity(spellEntity);

            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {

    }
}
