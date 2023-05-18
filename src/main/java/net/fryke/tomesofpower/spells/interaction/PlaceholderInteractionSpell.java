package net.fryke.tomesofpower.spells.interaction;

import net.fryke.tomesofpower.spells.types.InteractionSpell;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaceholderInteractionSpell extends InteractionSpell {
    @Override
    public void castSpell(World world, PlayerEntity caster, Hand hand) {
        HitResult hitBlock = MinecraftClient.getInstance().crosshairTarget;
        if(hitBlock.getType() == HitResult.Type.BLOCK) {
            BlockPos targetBlockPosition = BlockPos.ofFloored(hitBlock.getPos());
            world.breakBlock(targetBlockPosition, true);
        }
    }
}
