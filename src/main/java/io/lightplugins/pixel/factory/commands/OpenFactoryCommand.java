package io.lightplugins.pixel.factory.commands;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.factory.inventories.MainInventory;
import io.lightplugins.pixel.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class OpenFactoryCommand extends SubCommand {
    @Override
    public List<String> getName() {
        return List.of("open");
    }

    @Override
    public String getDescription() {
        return "Open the SkyFactory GUI";
    }

    @Override
    public String getSyntax() {
        return "/factory open";
    }

    @Override
    public int maxArgs() {
        return 1;
    }

    @Override
    public String getPermission() {
        return "lightpixel.factory.gui.factory";
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

        MainInventory factoryInventory = new MainInventory(player);
        factoryInventory.openInventory();

        return false;
    }

    @Override
    public boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException {
        Bukkit.getLogger().log(Level.INFO, Light.consolePrefix + "You can't use this command from the console!");
        return false;
    }
}
