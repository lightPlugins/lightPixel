package io.lightplugins.pixel.skills.commands;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.skills.LightSkills;
import io.lightplugins.pixel.util.NumberFormatter;
import io.lightplugins.pixel.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DummyCommand extends SubCommand {
    @Override
    public List<String> getName() {
        return Arrays.asList("give", "add");
    }

    @Override
    public String getDescription() {
        return "Gives the target player an amount of money.";
    }

    @Override
    public String getSyntax() {
        return "/eco [give,add] <player> <amount>";
    }

    @Override
    public int maxArgs() {
        return 3;
    }

    @Override
    public String getPermission() {
        return "lighteconomy.eco.command.give";
    }

    @Override
    public TabCompleter registerTabCompleter() {
        return (commandSender, command, s, args) -> {

            if(!commandSender.hasPermission(getPermission())) {
                return null;
            }

            if(args.length == 1) {
                return Arrays.asList("give", "add");
            }

            if (args.length == 2) {
                List<String> offlinePlayerNames = new ArrayList<>();
                for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
                    offlinePlayerNames.add(player.getName());
                }
                return offlinePlayerNames;
            }

            if(args.length == 3) {
                return List.of("<amount>");
            }

            return null;
        };
    }

    @Override
    public boolean performAsPlayer(Player player, String[] args) throws ExecutionException, InterruptedException {



        return false;
    }

    @Override
    public boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException {
        return false;
    }
}
