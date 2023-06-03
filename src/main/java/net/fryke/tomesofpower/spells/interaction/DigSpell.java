package net.fryke.tomesofpower.spells.interaction;

import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.item.tomes.TomeItem;
import net.fryke.tomesofpower.spells.SpellIdentifiers;
import net.fryke.tomesofpower.spells.types.InteractionSpell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
            // TODO this will break anything at all. need to restrict it
            world.breakBlock(blockHitResult.getBlockPos(), true);

            caster.getItemCooldownManager().set(tome, cooldownLengthTicks);
        } else {
            ToPMod.LOGGER.info("Hit result is not BlockHitResult");
        }
    }
}
