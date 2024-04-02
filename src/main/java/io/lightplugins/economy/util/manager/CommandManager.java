package io.lightplugins.economy.util.manager;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.economy.util.CompositeTabCompleter;
import io.lightplugins.economy.util.SubCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CommandManager implements CommandExecutor {


    private final ArrayList<SubCommand> subCommands;
    private ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    private void registerCommand(PluginCommand command) {
        if (command != null) {
            command.setExecutor(this);
            List<TabCompleter> tabCompletes = new ArrayList<>();

            LightEconomy.getDebugPrinting().print(
                    "Successfully registered command " + command.getName());

            for (SubCommand subCommand : getSubCommands()) {
                TabCompleter tabCompleter = subCommand.registerTabCompleter();
                if (tabCompleter != null) {
                    tabCompletes.add(tabCompleter);
                    LightEconomy.getDebugPrinting().print(
                            "Successfully registered tab completer for " + subCommand.getName());
                }
            }

            if (!tabCompletes.isEmpty()) {
                command.setTabCompleter(new CompositeTabCompleter(tabCompletes));
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
                    if (subCommand.getName().contains(args[0])) {

                        if (sender instanceof Player player) {
                            if(player.hasPermission(subCommand.getPermission())) {
                                if(args.length != subCommand.maxArgs()) {
                                    LightEconomy.getMessageSender().sendPlayerMessage(
                                            LightEco.getMessageParams().wrongSyntax()
                                                    .replace("#syntax#", subCommand.getSyntax()), player);
                                    return false;
                                }
                                try {
                                    subCommand.performAsPlayer(player, args);
                                    return true;
                                } catch (ExecutionException | InterruptedException e) {
                                    throw new RuntimeException("Something went wrong in executing " + Arrays.toString(args), e);
                                }
                            } else {
                                LightEconomy.getMessageSender().sendPlayerMessage(
                                        LightEco.getMessageParams().noPermission()
                                                .replace("#permission#", subCommand.getPermission()), player);
                                return false;
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
