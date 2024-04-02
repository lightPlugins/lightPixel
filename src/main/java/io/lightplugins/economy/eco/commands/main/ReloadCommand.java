package io.lightplugins.economy.eco.commands.main;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.bank.LightBank;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.economy.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReloadCommand extends SubCommand {
    @Override
    public List<String> getName() {
        return List.of("reload");
    }

    @Override
    public String getDescription() {
        return "Reload the configs of the specified module";
    }

    @Override
    public String getSyntax() {
        return "eco reload eco";
    }

    @Override
    public int maxArgs() {
        return 2;
    }

    @Override
    public String getPermission() {
        return "lighteconomy.eco.command.reload";
    }

    @Override
    public TabCompleter registerTabCompleter() {
        return ((commandSender, command, s, args) -> {

            if(!commandSender.hasPermission(getPermission())) {
                return null;
            }

            if(args.length == 1) {
                return List.of("reload");
            }

            if (args.length == 2) {
                return Arrays.asList("eco", "bank", "all", "virtual", "core");
            }

            return null;
        });
    }

    @Override
    public boolean performAsPlayer(Player player, String[] args) throws ExecutionException, InterruptedException {

        switch (args[1]) {
            case "eco" -> {
                LightEco.instance.reload();
                LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().moduleReload()
                        .replace("#module#", LightEco.instance.moduleName), player);
            }
            case "bank" -> {
                LightBank.instance.reload();
                LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().moduleReload()
                        .replace("#module#", LightBank.instance.moduleName), player);
            }
            case "all" -> {
                LightEco.instance.reload();
                LightBank.instance.reload();
                LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().reloadAll(), player);
            }
        }

        return false;
    }

    @Override
    public boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException {
        return false;
    }
}
