package net.fryke.epitome.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryke.epitome.EpiTomeMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TOMES_OF_POWER = FabricItemGroup.builder(new Identifier(EpiTomeMod.MOD_ID))
            .displayName(Text.literal("EpiTome"))
            .icon(() -> new ItemStack(ModItems.TESTING_TOME))
            .build();
}
