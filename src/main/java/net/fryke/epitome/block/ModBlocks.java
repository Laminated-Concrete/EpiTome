package net.fryke.epitome.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fryke.epitome.EpiTomeMod;
import net.fryke.epitome.entity.RitualBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block RITUAL_BLOCK = registerBlock("ritual_block",
            new RitualBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4.0f).requiresTool()));

    public static final BlockEntityType<RitualBlockEntity> RITUAL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(EpiTomeMod.MOD_ID, "ritual_block_entity"),
            FabricBlockEntityTypeBuilder.create(RitualBlockEntity::new, RITUAL_BLOCK).build()
    );

    public static void registerModBlocks() {
        EpiTomeMod.LOGGER.info("Registering Mod Blocks for " + EpiTomeMod.MOD_ID);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(EpiTomeMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(EpiTomeMod.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings()));
    }
}
