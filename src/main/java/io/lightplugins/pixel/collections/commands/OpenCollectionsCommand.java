package io.lightplugins.pixel.collections.commands;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.inventories.OverviewInventory;
import io.lightplugins.pixel.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class OpenCollectionsCommand extends SubCommand {
    @Override
    public List<String> getName() {
        return List.of("open");
    }

    @Override
    public String getDescription() {
        return "Open the Collection GUI";
    }

    @Override
    public String getSyntax() {
        return "/collections open";
    }

    @Override
    public int maxArgs() {
        return 1;
    }

    @Override
    public String getPermission() {
        return "lightpixel.collections.gui.open";
    }

    @Override
    public TabCompleter registerTabCompleter() {
        return ((commandSender, command, s, args) -> {

            if(!commandSender.hasPermission(getPermission())) {
                return null;
            }

            if(args.length == 1) {
                return getName();
            }

            return null;
        });
    }

    @Override
    public boolean performAsPlayer(Player player, String[] args) throws ExecutionException, InterruptedException {

        OverviewInventory overviewInventory = new OverviewInventory(player);
        overviewInventory.openInventory();

        return false;
    }

    @Override
    public boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException {
        Bukkit.getLogger().log(Level.INFO, Light.consolePrefix + "You can't use this command from the console!");
        return false;
    }
}
