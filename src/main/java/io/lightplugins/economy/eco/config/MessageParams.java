package io.lightplugins.economy.eco.config;

import io.lightplugins.economy.util.manager.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageParams {

    private final FileConfiguration config;

    public MessageParams(FileManager selectedLanguage) {
        this.config = selectedLanguage.getConfig();
    }

    public int version() { return config.getInt("version"); }

    public String prefix() { return config.getString("prefix"); }
    public String noPermission() { return config.getString("noPermission"); }
    public String wrongSyntax() { return config.getString("wrongSyntax"); }
    public String noNumber() { return config.getString("noNumber"); }
    public String onlyPositive() { return config.getString("onlyPositive"); }
    public String playerNotFound() { return config.getString("playerNotFound"); }
    public String depositSuccess() { return config.getString("depositSuccess"); }
    public String depositFailed() { return config.getString("depositFailed"); }

}