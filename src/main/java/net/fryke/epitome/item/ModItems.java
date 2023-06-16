package net.fryke.epitome.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.item.tomes.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static void registerModItems() {
        EpiTomeMod.LOGGER.info("Registering Mod Items for " + EpiTomeMod.MOD_ID);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EpiTomeMod.MOD_ID, name), item);
    }

    private static FabricItemSettings tomeSettings = new FabricItemSettings().maxCount(1).fireproof();

    /// Tome Registrations
    public static final Item TESTING_TOME = registerItem("testing_tome", new TestingTomeItem(tomeSettings));
    public static final Item EARTH_TOME = registerItem("earth_tome", new EarthTomeItem(tomeSettings));
    public static final Item AIR_TOME = registerItem("air_tome", new AirTomeItem(tomeSettings));
    public static final Item FIRE_TOME = registerItem("fire_tome", new FireTomeItem(tomeSettings));
    public static final Item WATER_TOME = registerItem("water_tome", new WaterTomeItem(tomeSettings));
}
