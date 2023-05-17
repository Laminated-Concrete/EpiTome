package net.fryke.tomesofpower.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryke.tomesofpower.ToPMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TOMES_TESTING = FabricItemGroup.builder(new Identifier(ToPMod.MOD_ID))
            .displayName(Text.literal("Testing Tomes"))
            .icon(() -> new ItemStack(ModItems.TESTING_TOME))
            .build();
}
