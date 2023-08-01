package net.fryke.epitome.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.block.ModBlocks;
import net.fryke.epitome.helpers.ModLogger;
import net.fryke.epitome.item.tomes.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static void registerModItems() {
        ModLogger.log("Registering Mod Items for " + EpiTomeMod.MOD_ID);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EpiTomeMod.MOD_ID, name), item);
    }

    private static final FabricItemSettings tomeSettings = new FabricItemSettings().maxCount(0).fireproof().maxDamage(0);

    public static final Item RECEPTACLE_ITEM = registerItem("receptacle_block", new ReceptacleItem(ModBlocks.RECEPTACLE_BLOCK, new FabricItemSettings()));

    /// This is the item used for the patchouli book
    public static final Item KNOWLEDGE_TOME = registerItem("knowledge_tome", new KnowledgeTomeItem(tomeSettings));

    /// Tome Registrations
    public static final Item TESTING_TOME = registerItem("testing_tome", new TestingTomeItem(tomeSettings));
    public static final Item EARTH_TOME = registerItem("earth_tome", new EarthTomeItem(tomeSettings));
    public static final Item AIR_TOME = registerItem("air_tome", new AirTomeItem(tomeSettings));
    public static final Item FIRE_TOME = registerItem("fire_tome", new FireTomeItem(tomeSettings));
    public static final Item WATER_TOME = registerItem("water_tome", new WaterTomeItem(tomeSettings));
}
