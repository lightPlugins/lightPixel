package io.lightplugins.economy.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CompositeTabCompleter implements TabCompleter {
    private final List<TabCompleter> tabCompletes;

    public CompositeTabCompleter(List<TabCompleter> tabCompletes) {
        this.tabCompletes = tabCompletes;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        for (TabCompleter tabCompleter : tabCompletes) {
            if (tabCompleter != null) {
                List<String> tabCompletions = tabCompleter.onTabComplete(sender, command, alias, args);
                if (tabCompletions != null) {
                    completions.addAll(tabCompletions);
                }
            }
        }
        return completions;
    }
}
