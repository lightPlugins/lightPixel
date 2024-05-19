package io.lightplugins.pixel.factory;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.factory.api.LightFactoryAPI;
import io.lightplugins.pixel.factory.commands.OpenFactoryCommand;
import io.lightplugins.pixel.factory.config.MessageParams;
import io.lightplugins.pixel.factory.config.SettingParams;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.CommandManager;
import io.lightplugins.pixel.util.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;

public class LightFactory implements LightModule {


    public static LightFactory instance;
    public static LightFactoryAPI lightProfilesAPI;
    public boolean isModuleEnabled = false;

    public final String moduleName = "factory";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();



    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;
    private FileManager mainMenu;

    @Override
    public void enable() {

        instance = this;
        lightProfilesAPI = new LightFactoryAPI();
        initFiles();
        this.settingParams = new SettingParams(this);
        selectLanguage();
        messageParams = new MessageParams(language);
        initSubCommands();
        registerEvents();
        isModuleEnabled = true;
        Light.getDebugPrinting().print(
                "Successfully started module " + this.moduleName + "!");

    }

    @Override
    public void disable() {

    }

    @Override
    public void reload() {

        getSettings().reloadConfig(moduleName + "/settings.yml");
        Light.getDebugPrinting().print(moduleName + "/settings.yml");
        selectLanguage();
        Light.getDebugPrinting().print(moduleName + "/language/" + settingParams.getModuleLanguage() + ".yml");
        getLanguage().reloadConfig(moduleName + "/language/" + settingParams.getModuleLanguage() + ".yml");
        getMainMenu().reloadConfig(moduleName + "/main-menu.yml");
        Light.getDebugPrinting().print("Successfully reloaded module " + moduleName);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return moduleName;
    }

    private void initFiles() {
        this.settings = new FileManager(
                Light.instance, moduleName + "/settings.yml", true);
        this.mainMenu = new FileManager(
                Light.instance, moduleName + "/inventories/main-menu.yml", true);
    }

    private void selectLanguage() {
        this.language = Light.instance.selectLanguage(settingParams.getModuleLanguage(), moduleName);
    }

    private void initSubCommands() {
        PluginCommand factoryCommand = Bukkit.getPluginCommand("factory");
        subCommands.add(new OpenFactoryCommand());
        new CommandManager(factoryCommand, subCommands);

    }

    public void registerEvents() {

    }

    public SettingParams getSettingParams() {
        return settingParams;
    }

    public static MessageParams getMessageParams() {
        return messageParams;
    }

    public FileManager getSettings() { return settings; }

    public FileManager getLanguage() { return language; }

    public FileManager getMainMenu() { return mainMenu; }
}
