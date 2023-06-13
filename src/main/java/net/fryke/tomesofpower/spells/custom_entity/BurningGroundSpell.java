package net.fryke.tomesofpower.spells.custom_entity;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.entity.ModEntities;
import net.fryke.tomesofpower.entity.spell.custom.BurningGroundSpellEntity;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.CustomEntitySpell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
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
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = caster.raycast(spellRange, 0.0F, false);
        if(hitResult.getType() == HitResult.Type.MISS) { return; }
        if(hitResult instanceof BlockHitResult) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
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
