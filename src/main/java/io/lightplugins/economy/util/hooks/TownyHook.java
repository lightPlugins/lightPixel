package io.lightplugins.economy.util.hooks;

import io.lightplugins.economy.LightEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TownyHook {


    public boolean isTowny() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Towny");
        if (plugin != null) {
            if(!plugin.getDescription().getVersion().equals("0.100.1.0")) {
                Bukkit.getConsoleSender().sendMessage(
                        LightEconomy.consolePrefix + "Towny is present but the version §e"
                                + plugin.getDescription().getVersion() +
                                "§r is not supported. Please update to version §e0.100.1.0 or higher");
                return false;
            } else {
                LightEconomy.getDebugPrinting().print("Towny is present and supported. Hooking into Towny");
                return true;
            }

        }
        return false;
    }

}
