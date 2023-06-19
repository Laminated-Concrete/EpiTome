package net.fryke.epitome.rituals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.loader.api.FabricLoader;
import net.fryke.epitome.EpiTomeMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class RitualManager {
    private static RitualManager instance;
    private Map<Identifier, RitualStructure> structureMap;

    public static final Identifier KNOWLEDGE_RITUAL_ID = new Identifier(EpiTomeMod.MOD_ID, "knowledge_ritual");
    public static final Identifier EARTH_RITUAL_ID = new Identifier(EpiTomeMod.MOD_ID, "earth_ritual");

    public RitualManager() {
        structureMap = new HashMap<>();
    }

    public static RitualManager getInstance() {
        if (instance == null) {
            instance = new RitualManager();
        }
        return instance;
    }

    public void addRitual(Identifier structureId, RitualStructure ritualStructure) {
        structureMap.put(structureId, ritualStructure);
    }

    public RitualStructure getRitualStructure(Identifier structureId) {
        return structureMap.get(structureId);
    }

    public void parseRitual(Identifier structureId, JsonElement jsonElement) {
        Gson gson = new Gson();
        RitualStructure ritualStructure = gson.fromJson(jsonElement, RitualStructure.class);
        this.addRitual(structureId, ritualStructure);
    }

    public Identifier determineRitual(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // we determine the ritual by the block underneath the ritual block
        for (Map.Entry<Identifier, RitualStructure> entry : structureMap.entrySet()) {
            String signatureBlockId = entry.getValue().signatureBlockId();
            Block targetBlock = world.getBlockState(pos.add(0, -1, 0)).getBlock();
            String targetBlockId = Registries.BLOCK.getId(targetBlock).toString();
            EpiTomeMod.LOGGER.info("checking signatureBlockId = "  + signatureBlockId + " against " + targetBlockId);

            if(signatureBlockId.equals(targetBlockId)) {
                EpiTomeMod.LOGGER.info("we found a matching ritual = " + entry.getKey());
                // we have a match, return the ID
                return entry.getKey();
            }
        }

        // if we reach this point, we have failed to find a matching ritual
        return null;
    }

    public Boolean checkStructure(World world, BlockPos ritualBlockPos, Identifier ritualStructureId) {
        RitualStructure ritualStructure = this.getRitualStructure(ritualStructureId);
        if(ritualStructure == null) {
            return false;
        }

        Boolean overallValid = false;
        // here we loop through numbers 0-3 and use them, multiplied by 90, to iterate over the possible rotations of 90, 180, and 270 degrees
        for (int i = 0; i < 4; i++) {
            Boolean isValid = this.isValidStructure(world, ritualBlockPos, ritualStructure, 90 * i);
            if(isValid) {
                // if we find a valid structure, break out right away and set to true
                overallValid = true;
                break;
            }
        }

        return overallValid;
    }

    public Boolean isValidStructure(World world, BlockPos ritualBlockPos, RitualStructure ritualStructure, int rotationDegrees) {
        Boolean validStructure = true; // we start assuming we are good to go
        for (BlockEntry blockEntry : ritualStructure.blockEntries()) {
            // we get our data ready
            Vec3i offset = blockEntry.blockPosOffset();
            String[] allowedBlockIds = blockEntry.allowedBlockTypeIds();

            // we get the target pos, rotate it, and get the block at that location
            BlockPos targetPos = ritualBlockPos.add(offset);
            BlockPos targetRotatedPos = this.rotatePoint(targetPos, ritualBlockPos, rotationDegrees);
            Block targetBlock = world.getBlockState(targetRotatedPos).getBlock();

            // if we have allowed blocks, that means we are looking for something specific
            if(allowedBlockIds.length != 0) {
                // we grab the target block's ID, convert it to a string, and check it against the string IDs in the array
                if(!Arrays.stream(allowedBlockIds).anyMatch((blockIdString) -> {
                    String targetBlockId = Registries.BLOCK.getId(targetBlock).toString();
                    return blockIdString.equals(targetBlockId);
                })) {
                    // if the current block does not match any of our allowed blocks, we break and set false because we must have an allowed block
                    validStructure = false;
                    break;
                }
            }
        }
        return validStructure;
    }

    public static BlockPos rotatePoint(Vec3i point, Vec3i center, int degrees) {
        // rotation math is pretty good
        double angle = Math.toRadians(degrees);

        int dx = point.getX() - center.getX();
        int dy = point.getY() - center.getY();
        int dz = point.getZ() - center.getZ();

        int rotatedX = center.getX() + (int) Math.round(dx * Math.cos(angle) - dz * Math.sin(angle));
        int rotatedY = point.getY();
        int rotatedZ = center.getZ() + (int) Math.round(dx * Math.sin(angle) + dz * Math.cos(angle));

        return new BlockPos(rotatedX, rotatedY, rotatedZ);
    }

    /**
     * This method is used via a chat command for generating ritual structure JSON files automatically.
     * see {@link net.fryke.epitome.commands.ModCommands} for the command that connects with this method.
     *
     * @param player The player entity using the command.
     * @param world The world the player entity is in.
     * @param fileName The file name (without the file extension).
     * @param posXDist The distance in the positive X direction that should be included in the structure.
     * @param negXDist The distance in the negative X direction that should be included in the structure.
     * @param posYDist The distance in the positive Y direction that should be included in the structure.
     * @param negYDist The distance in the negative Y direction that should be included in the structure.
     * @param posZDist The distance in the positive Z direction that should be included in the structure.
     * @param negZDist The distance in the negative Z direction that should be included in the structure.
     * @param ignoreAllAir Whether all air blocks should be completely ignored.
     * @param enforceAllAir Whether all air blocks should be hard enforced.
     * @return Returns the path to the saved file.
     */
    public static String generateRitualJson(PlayerEntity player, World world, String fileName, int posXDist, int negXDist, int posYDist, int negYDist, int posZDist, int negZDist, Boolean ignoreAllAir, Boolean enforceAllAir) {
        if(player == null || world == null) {
            return null;
        }

        BlockPos ritualBlockPos = player.getBlockPos().add(0, -1, 0);
        int startX = ritualBlockPos.getX() - negXDist;
        int startY = ritualBlockPos.getY() - negYDist;
        int startZ = ritualBlockPos.getZ() - negZDist;

        int endX = ritualBlockPos.getX() + posXDist;
        int endY = ritualBlockPos.getY() + posYDist;
        int endZ = ritualBlockPos.getZ() + posZDist;

        List<BlockEntry> blockEntries = new ArrayList<>();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    if(currentPos.equals(ritualBlockPos)) {
                        continue;
                    }

                    Block block = world.getBlockState(currentPos).getBlock();
                    Identifier blockId = Registries.BLOCK.getId(world.getBlockState(currentPos).getBlock());
                    BlockPos offset = currentPos.subtract(ritualBlockPos);
                    List<String> allowedBlockIds = new ArrayList<>();

                    if(currentPos.equals(ritualBlockPos.add(0, 1, 0))) {
                        allowedBlockIds.add("minecraft:air");
                    } else if(block == Blocks.AIR) {
                        if(ignoreAllAir) {
                            // if we want to ignore all air blocks, do nothing
                            continue;
                        }

                        if(enforceAllAir || block == Blocks.BEDROCK) {
                            // if we are enforcing all air blocks, or it matches the placeholder block, we add it
                            allowedBlockIds.add("minecraft:air");
                        } else {
                            continue;
                        }
                    } else {
                        // otherwise we are dealing with a normal block
                        allowedBlockIds.add(blockId.toString());
                    }

                    BlockEntry entry = new BlockEntry(offset, allowedBlockIds.toArray(new String[0]));
                    blockEntries.add(entry);
                }
            }
        }

        Block signatureBlock = world.getBlockState(ritualBlockPos.add(0, -1, 0)).getBlock();
        Identifier signatureBlockId = Registries.BLOCK.getId(signatureBlock);

        // now we create the RitualStructure
        RitualStructure ritualStructure = new RitualStructure(signatureBlockId.toString(), blockEntries.toArray(new BlockEntry[0]));

        // then we want to generate the JSON
        Gson gson = new Gson();
        String json = gson.toJson(ritualStructure, RitualStructure.class);

        try {
            Path gameDir = FabricLoader.getInstance().getGameDirectory().toPath();
            Path outputPath = gameDir.resolve(fileName + ".json");

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()));
            writer.write(json);
            writer.close();

            return outputPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

record RitualStructure(String signatureBlockId, BlockEntry[] blockEntries) {

}

record BlockEntry(Vec3i blockPosOffset, String[] allowedBlockTypeIds) {

}