package io.lightplugins.economy.util;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;

import java.util.concurrent.ExecutionException;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract String getPermission();
    public abstract TabCompleter registerTabCompleter();
    public abstract boolean performAsPlayer(CommandSender player, String[] args) throws ExecutionException, InterruptedException;
    public abstract boolean performAsConsole(ConsoleCommandSender sender, String[] args) throws ExecutionException, InterruptedException;
}
