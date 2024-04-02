package io.lightplugins.economy.bank;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.bank.config.SettingParams;
import io.lightplugins.economy.util.SubCommand;
import io.lightplugins.economy.util.interfaces.LightModule;
import io.lightplugins.economy.util.manager.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class LightBank implements LightModule {

    public static LightBank instance;
    public boolean isModuleEnabled = false;

    public final String moduleName = "bank";
    public final String adminPerm = "lighteconomy." + moduleName + ".admin";
    public final String tablePrefix = "lightbank_";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public SettingParams settingParams;

    private FileManager settings;
    private FileManager language;


    @Override
    public void enable() {
        LightEconomy.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Starting " + this.moduleName + " module...");
        instance = this;
        LightEconomy.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Creating default files for module " + this.moduleName + " module...");
        initFiles();
        this.settingParams = new SettingParams(this);
        LightEconomy.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Selecting module language for module " + this.moduleName + "...");
        selectLanguage();
        LightEconomy.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Registering subcommands for module " + this.moduleName + "...");
        initSubCommands();
        this.isModuleEnabled = true;
        LightEconomy.getDebugPrinting().print(LightEconomy.consolePrefix +
                "Successfully started module " + this.moduleName + "!");

    }

    @Override
    public void disable() {

    }

    @Override
    public void reload() { enable(); }

    @Override
    public boolean isEnabled() {
        return isModuleEnabled;
    }

    @Override
    public String getName() {
        return moduleName;
    }

    public FileConfiguration getSettings() { return this.settings.getConfig(); }

    public FileConfiguration getLanguage() { return this.language.getConfig(); }

    private void initFiles() {
        this.settings = new FileManager(
                LightEconomy.instance, moduleName + "/settings.yml", true);
    }

    private void selectLanguage() {
        this.language = LightEconomy.instance.selectLanguage(this.settingParams.getModuleLanguage(), moduleName);
    }

    private void initSubCommands() {

    }
}
