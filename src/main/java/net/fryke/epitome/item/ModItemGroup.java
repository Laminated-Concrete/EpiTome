package net.fryke.epitome.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TOMES_OF_POWER = Registry.register(Registries.ITEM_GROUP,
        new Identifier(EpiTomeMod.MOD_ID, "epitome_group"),
        FabricItemGroup.builder()
            .displayName(Text.literal("EpiTome"))
            .icon(() -> new ItemStack(ModItems.TESTING_TOME))
            .entries(((displayContext, entries) -> {
                // The order these are added = the order they are displayed
                entries.add(ModItems.KNOWLEDGE_TOME);
                entries.add(ModItems.TESTING_TOME);
                entries.add(ModItems.AIR_TOME);
                entries.add(ModItems.EARTH_TOME);
                entries.add(ModItems.FIRE_TOME);
                entries.add(ModItems.WATER_TOME);

                entries.add(ModBlocks.RECEPTACLE_BLOCK);
            }))
            .build());

    public static void registerModItemGroups(){}

    /*
        Example of adding a thing to an existing item group
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.TESTING_TOME);
        });
     */
}
