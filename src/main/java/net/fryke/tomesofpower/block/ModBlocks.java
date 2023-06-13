package net.fryke.tomesofpower.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fryke.tomesofpower.ToPMod;
import net.fryke.tomesofpower.entity.RitualBlockEntity;
import net.fryke.tomesofpower.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block RITUAL_BLOCK = registerBlock("ritual_block",
            new RitualBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool()), ModItemGroup.TOMES_OF_POWER);

    public static final BlockEntityType<RitualBlockEntity> RITUAL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(ToPMod.MOD_ID, "ritual_block_entity"),
            FabricBlockEntityTypeBuilder.create(RitualBlockEntity::new, RITUAL_BLOCK).build()
    );

    public static void registerModBlocks() {
        ToPMod.LOGGER.info("Registering Mod Blocks for " + ToPMod.MOD_ID);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(ToPMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(ToPMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        // note will only add the block to a single item group. would need to add more logic for multiple groups
        return item;
    }
}
