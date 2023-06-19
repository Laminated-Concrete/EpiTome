package net.fryke.epitome.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fryke.epitome.rituals.RitualManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static com.mojang.brigadier.arguments.BoolArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class ModCommands {
    public static void initializeModCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("epitome")
                .then(literal("generateRitual")
                .then(argument("file_name", word())
                .then(argument("+x", integer())
                .then(argument("-x", integer())
                .then(argument("+y", integer())
                .then(argument("-y", integer())
                .then(argument("+z", integer())
                .then(argument("-z", integer())
                .then(argument("ignore_all_air", bool())
                .then(argument("enforce_all_air", bool())
                .executes(context -> {
                    final ServerPlayerEntity player = context.getSource().getPlayer();
                    final World world = context.getSource().getWorld();
                    String savedPath = RitualManager.generateRitualJson(
                        player,
                        world,
                        context.getArgument("file_name", String.class),
                        context.getArgument("+x", Integer.class),
                        context.getArgument("-x", Integer.class),
                        context.getArgument("+y", Integer.class),
                        context.getArgument("-y", Integer.class),
                        context.getArgument("+z", Integer.class),
                        context.getArgument("-z", Integer.class),
                        context.getArgument("ignore_all_air", Boolean.class),
                        context.getArgument("enforce_all_air", Boolean.class)
                    );

                    if(savedPath == null) {
                        context.getSource().sendMessage(Text.literal("There was a problem saving the JSON file"));
                    } else {
                        context.getSource().sendMessage(Text.literal("Saved JSON file to " + savedPath));
                    }
                    return 1;
            }))))))))))));
        }));
    }
}
