package io.lightplugins.economy.util.manager;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.util.SubCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CommandManager implements CommandExecutor {


    private final ArrayList<SubCommand> subCommands;
    private ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    private void registerCommand(PluginCommand command) {
        if (command != null) {
            command.setExecutor(this);
            LightEconomy.getDebugPrinting().print(
                    "Successfully registered command " + command.getName());
            for (SubCommand subCommand : getSubCommands()) {
                if(subCommand.registerTabCompleter() == null) {
                    continue;
                }
                command.setTabCompleter(subCommand.registerTabCompleter());
                LightEconomy.getDebugPrinting().print(
                        "Successfully registered tab completer for " + command.getName());
            }
        }
    }

    public CommandManager(PluginCommand command, ArrayList<SubCommand> subCommands) {
        this.subCommands = subCommands;
        registerCommand(command);
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {


            if (args.length > 0) {
                for (SubCommand subCommand : getSubCommands()) {
                    if (args[0].equalsIgnoreCase(subCommand.getName())) {

                        if (sender instanceof Player player) {
                            if(player.hasPermission(subCommand.getPermission())) {
                                try {
                                    subCommand.performAsPlayer(player, args);
                                    return true;
                                } catch (ExecutionException | InterruptedException e) {
                                    throw new RuntimeException("Something went wrong in executing " + Arrays.toString(args), e);
                                }
                            }
                        }

                        if (sender instanceof ConsoleCommandSender console) {
                            try {
                                subCommand.performAsConsole(console, args);
                                return true;
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException("Something went wrong in executing " + Arrays.toString(args), e);
                            }
                        }
                    }
                }
            }

        return false;
    }
}
