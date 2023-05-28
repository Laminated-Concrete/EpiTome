package net.fryke.tomesofpower.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.item.tomes.EarthTomeItem;
import net.fryke.tomesofpower.item.tomes.TestingTomeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static void registerModItems() {
        ToPMod.LOGGER.info("Registering Mod Items for " + ToPMod.MOD_ID);
    }

    private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(ToPMod.MOD_ID, name), item);
    }

    /// Tome Registrations
    public static final Item TESTING_TOME = registerItem("testing_tome", new TestingTomeItem(new FabricItemSettings().maxCount(1).fireproof()), ModItemGroup.TOMES_OF_POWER);
    public static final Item EARTH_TOME = registerItem("earth_tome", new EarthTomeItem(new FabricItemSettings().maxCount(1).fireproof()), ModItemGroup.TOMES_OF_POWER);
}
