package net.fryke.tomesofpower.spells.interaction;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.yarn.constants.MiningLevels;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.InteractionSpell;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DigSpell extends InteractionSpell {
    public DigSpell() {
        super();
        SPELL_ID = SpellIdentifiers.DIG_SPELL_ID;
        cooldownLengthTicks = 5; // 0.25s
    }

    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
        if (hitResult instanceof BlockHitResult) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockState blockState = world.getBlockState(blockHitResult.getBlockPos());

            int requiredMiningLevel = MiningLevelManager.getRequiredMiningLevel(blockState);
            float hardness = blockState.getHardness(world, blockHitResult.getBlockPos());

            if(requiredMiningLevel <= MiningLevels.DIAMOND && hardness != -1) {
                world.breakBlock(blockHitResult.getBlockPos(), true);
            }

            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        }
    }

    @Override
    public void castSpellClient(World world, PlayerEntity caster, Hand hand, TomeItem tome) {
        HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
        if (hitResult instanceof BlockHitResult) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            world.addParticle(ParticleTypes.POOF, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0, 0, 0);
        }
    }
}
