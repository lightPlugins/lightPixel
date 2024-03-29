package io.lightplugins.economy;

import io.lightplugins.economy.bank.LightBank;
import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.light.Light;
import io.lightplugins.light.api.LightAPI;
import io.lightplugins.light.api.LightModule;
import io.lightplugins.light.api.database.SQLDatabase;
import io.lightplugins.light.api.files.FileManager;
import io.lightplugins.light.api.util.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LightEconomy extends JavaPlugin {


    public static LightEconomy instance;
    public static LightAPI api;
    private LightEco lightEco;
    private LightBank lightBank;
    private Map<String, LightModule> modules = new HashMap<>();
    private static MessageSender messageSender;

    public final static String consolePrefix = "§r[light§eEconomy§r] §r";

    private FileManager settings;


    public void onLoad() {

        instance = this;

        if (Bukkit.getPluginManager().getPlugin("light") == null) {
            Bukkit.getConsoleSender().sendMessage("""


                        §4ERROR

                        §cCould not found §4Light\s
                        §rLighteconomy will §cnot run §rwithout Light. Please download
                        the latest version of Light\s
                        §chttps://www.linkToLight.de/\s


                    """);
            onDisable();
            return;
        } else {
            api = Light.api;
            Bukkit.getConsoleSender().sendMessage(consolePrefix + "Successfully hooked into §eLight");
        }
        this.modules = new LinkedHashMap<>();
    }

    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("\n " +
                " §r_      _____ _____ _    _ _______ §e______ _____ ____  _   _  ____  __  ____     __\n" +
                " §r| |    |_   _/ ____| |  | |__   __§e|  ____/ ____/ __ \\| \\ | |/ __ \\|  \\/  \\ \\   / /\n" +
                " §r| |      | || |  __| |__| |  | |  §e| |__ | |   | |  | |  \\| | |  | | \\  / |\\ \\_/ / \n" +
                " §r| |      | || | |_ |  __  |  | |  §e|  __|| |   | |  | | . ` | |  | | |\\/| | \\   /  \n" +
                " §r| |____ _| || |__| | |  | |  | |  §e| |___| |___| |__| | |\\  | |__| | |  | |  | |   \n" +
                " §r|______|_____\\_____|_|  |_|  |_|  §e|______\\_____\\____/|_| \\_|\\____/|_|  |_|  |_|" +
                "\n\n" + ChatColor.RESET +
                "      Version: §e6.0.0   §rAuthor: §elightPlugins\n" +
                "      §rThank you for using lightEconomy. If you came in trouble feel free to join\n" +
                "      my §eDiscord §rserver: https://discord.gg/G2EuzmSW\n");

        api.getDebugPrinting().print(consolePrefix + "Loading lightEconomy modules...");
        this.messageSender = new MessageSender();
        initModules();
        loadModules();

    }

    public void onDisable() {

        Iterator<LightModule> iterator = this.modules.values().iterator();

        while(iterator.hasNext()) {
            this.unloadModule(iterator.next());
            iterator.remove();
        }

    }

    private void loadModules() {

        this.loadModule(lightEco, true);
        this.loadModule(lightBank, false);

    }

    private void loadModule(LightModule lightModule, boolean enable) {
        if(lightModule.isEnabled()) {
            api.getDebugPrinting().print(consolePrefix + "Module §e" + lightModule.getName() + "§r already loaded.");
            return;
        }
        api.getDebugPrinting().print(consolePrefix + "Module §e" + lightModule.getName() + "§r is" +
                (enable ? "§a activated" : "§c deactivated"));
        if(enable) { lightModule.enable();  }

    }

    private void unloadModule(LightModule lightModule) {
        if(!lightModule.isEnabled()) {
            return;
        }
        lightModule.disable();
        api.getDebugPrinting().print(consolePrefix + "Successfully unloaded module: §e" + lightModule.getName());
    }

    private void initModules() {

        this.lightEco = new LightEco();
        this.lightBank = new LightBank();

        this.modules.put(this.lightEco.getName(), this.lightEco);
        this.modules.put(this.lightBank.getName(), this.lightBank);
    }

    public FileManager selectLanguage(String languageName, String moduleName) {

        return switch (languageName) {
            case "de" -> {
                api.getDebugPrinting().print(
                        consolePrefix + "Selected language for module " + moduleName + ": " + languageName);
                yield new FileManager(LightEconomy.instance, moduleName +
                        "/language/de.yml", true);
            }
            case "pl" -> {
                api.getDebugPrinting().print(
                        consolePrefix + "Selected language for module " + moduleName + ": " + languageName);
                yield new FileManager(LightEconomy.instance, moduleName +
                        "/language/pl.yml", true);
            }
            default -> {
                api.getDebugPrinting().print(
                        consolePrefix + "Selected language for module " + moduleName + ": " + languageName);
                yield new FileManager(LightEconomy.instance, moduleName +
                        "/language/en.yml", true);
            }
        };
    }

    public static MessageSender getMessageSender() {
        return messageSender;
    }
}