package net.fryke.epitome.rituals;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fryke.epitome.EpiTomeMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;

public class RitualStructures {
    private static SimpleRegistry ritualStrictureRegistry = FabricRegistryBuilder.createSimple(RitualStructure.class, new Identifier(EpiTomeMod.MOD_ID, "ritual_structure_type")).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void registerModRitualStructures() {
        EpiTomeMod.LOGGER.info("Registering Mod Ritual Structures for " + EpiTomeMod.MOD_ID);
    }

    public static RitualStructure registerRitualStructure(Identifier ritualStructureIdentifier, RitualStructure ritualStructure) {
        return Registry.register(ritualStrictureRegistry, ritualStructureIdentifier, ritualStructure);
    }

    public static RitualStructure getRitualStructure(Identifier ritualStructureId) {
        return (RitualStructure) ritualStrictureRegistry.get(ritualStructureId);
    }

    public static final Identifier EARTH_RITUAL_START_ID = new Identifier(EpiTomeMod.MOD_ID, "earth_ritual_start");
    public static final RitualStructure EARTH_RITUAL_START = registerRitualStructure(EARTH_RITUAL_START_ID,
        new RitualStructure(RitualIdentifiers.EARTH_RITUAL_ID, new BlockEntry[]{
            new BlockEntry(new Vec3i(2 ,0, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,0, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,0, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,0, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,1, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,1, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,1, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,1, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,2, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,2, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,2, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,2, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,3, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,3, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,3, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,3, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(1 ,0, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-1 ,0, 2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(1 ,0, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-1 ,0, -2), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,0, 1), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,0, 1), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(2 ,0, -1), new Block[]{Blocks.BOOKSHELF}, null),
            new BlockEntry(new Vec3i(-2 ,0, -1), new Block[]{Blocks.BOOKSHELF}, null),
    }));
}

record RitualStructure(Identifier ritualId, BlockEntry[] blockEntries) { }
record BlockEntry(Vec3i blockPosOffset, Block[] allowedBlockTypes, Block[] disallowedBlockTypes) { }