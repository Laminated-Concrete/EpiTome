package net.fryke.testingmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fryke.testingmod.TestingMod;
import net.fryke.testingmod.item.custom.TomeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item TESTING_TOME = registerItem("testing_tome",
            new TomeItem(new FabricItemSettings().maxCount(1)), ModItemGroup.TOMES_TESTING);

    public static final Item TESTING_ITEM_2 = registerItem("testing_item_2",
            new Item(new FabricItemSettings()), ModItemGroup.TOMES_TESTING);

    public static void registerModItems() {

        TestingMod.LOGGER.info("Registering Mod Items for " + TestingMod.MOD_ID);
    }
    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(TestingMod.MOD_ID, name), item);
    }
}
