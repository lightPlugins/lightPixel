package io.lightplugins.economy.eco.commands.eco;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.economy.util.NumberFormatter;
import io.lightplugins.economy.util.SubCommand;
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

public class EcoSetCommand extends SubCommand {
    @Override
    public List<String> getName() {
        return List.of("set");
    }

    @Override
    public String getDescription() {
        return "Set the target player an amount of money.";
    }

    @Override
    public String getSyntax() {
        return "/eco set <player> <amount>";
    }

    @Override
    public int maxArgs() {
        return 3;
    }

    @Override
    public String getPermission() {
        return "lighteconomy.eco.command.set";
    }

    @Override
    public TabCompleter registerTabCompleter() {
        return ((commandSender, command, s, args) -> {

            if(!commandSender.hasPermission(getPermission())) {
                return null;
            }

            if(args.length == 1) {
                return List.of("set");
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
        });
    }

    @Override
    public boolean performAsPlayer(Player player, String[] args) throws ExecutionException, InterruptedException {

        OfflinePlayer target = Bukkit.getPlayer(args[1]);

        if(target == null) {
            LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().playerNotFound(), player);
            return false;
        }

        if(!NumberFormatter.isNumber(args[2])) {
            if(!NumberFormatter.isShortNumber(args[2])) {
                LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().noNumber(), player);
                return false;
            }
        }

        BigDecimal bg = NumberFormatter.parseMoney(args[2]);

        if(bg == null) {
            LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().noNumber(), player);
            return false;
        }

        if(!NumberFormatter.isPositiveNumber(bg.doubleValue())) {
            LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().onlyPositive(), player);
            return false;
        }

        LightEco.instance.getQueryManager().setBalanceFromAccount(target.getUniqueId(), bg)
                .thenAcceptAsync(balanceResult -> {
                    if(balanceResult > 0) {
                        LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().setSuccess()
                                .replace("#player#", target.getName())
                                .replace("#amount#", NumberFormatter.formatForMessages(bg)), player);
                        return;
                    }
                    LightEconomy.getMessageSender().sendPlayerMessage(LightEco.getMessageParams().setFailed()
                            .replace("#player#", target.getName())
                            .replace("#reason#", "Something went wrong. Check your logs immediately.")
                            .replace("#amount#", NumberFormatter.formatForMessages(bg)), player);
                });

        return false;
    }

    @Override
    public boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException {
        return false;
    }
}
