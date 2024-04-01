package io.lightplugins.economy.util;

import io.lightplugins.economy.LightEconomy;
import org.bukkit.Bukkit;

public class DebugPrinting {

    public void print(String message) {
        Bukkit.getConsoleSender().sendMessage(LightEconomy.consolePrefix + message);
    }
}
