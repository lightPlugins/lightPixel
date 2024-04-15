package io.lightplugins.pixel.util.manager;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.skills.LightSkills;
import io.lightplugins.pixel.util.CompositeTabCompleter;
import io.lightplugins.pixel.util.SubCommand;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class CommandManager implements CommandExecutor {


    private final ArrayList<SubCommand> subCommands;
    private ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    private void registerCommand(PluginCommand command) {
        if (command != null) {
            command.setExecutor(this);
            Map<String, TabCompleter> subCommandTabCompleters = new HashMap<>();
            List<String> ecoSubCommands = new ArrayList<>(); // Liste der Subcommands von /eco

            Light.getDebugPrinting().print(
                    "Successfully registered command " + command.getName());

            for (SubCommand subCommand : getSubCommands()) {
                TabCompleter tabCompleter = subCommand.registerTabCompleter();
                if (tabCompleter != null) {
                    List<String> subCommandNames = subCommand.getName();
                    for (String subCommandName : subCommandNames) {
                        subCommandTabCompleters.put(subCommandName, tabCompleter);
                        ecoSubCommands.add(subCommandName); // Füge den Subcommand-Namen zur Liste hinzu
                        Light.getDebugPrinting().print(
                                "Successfully registered tab completer for " + subCommandName);
                    }
                }
            }

            if (!subCommandTabCompleters.isEmpty()) {
                command.setTabCompleter(new CompositeTabCompleter(subCommandTabCompleters, ecoSubCommands));
            }
        }
    }

    public CommandManager(PluginCommand command, ArrayList<SubCommand> subCommands) {
        this.subCommands = subCommands;
        registerCommand(command);
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {


            if (args.length > 0) {
                for (SubCommand subCommand : getSubCommands()) {
                    if (subCommand.getName().contains(args[0])) {

                        if (sender instanceof Player player) {
                            if(player.hasPermission(subCommand.getPermission())) {
                                if(args.length != subCommand.maxArgs()) {
                                    Light.getMessageSender().sendPlayerMessage(
                                            LightSkills.getMessageParams().wrongSyntax()
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
                                Light.getMessageSender().sendPlayerMessage(
                                        LightSkills.getMessageParams().noPermission()
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
