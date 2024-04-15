package io.lightplugins.pixel.collections;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.commands.OpenCollectionsCommand;
import io.lightplugins.pixel.collections.config.MessageParams;
import io.lightplugins.pixel.collections.config.SettingParams;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.CommandManager;
import io.lightplugins.pixel.util.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;

public class LightCollections implements LightModule {

    public static LightCollections instance;
    public boolean isModuleEnabled = false;

    public final String moduleName = "collections";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;
    private FileManager categories;



    @Override
    public void enable() {

        instance = this;
        initFiles();
        this.settingParams = new SettingParams(this);
        selectLanguage();   // must be called before messageParams = new ...
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
        this.categories = new FileManager(
                Light.instance, moduleName + "/categories.yml", false);
    }

    private void selectLanguage() {
        this.language = Light.instance.selectLanguage(settingParams.getModuleLanguage(), moduleName);
    }

    public SettingParams getSettingParams() {
        return settingParams;
    }

    public static MessageParams getMessageParams() {
        return messageParams;
    }

    public FileManager getCategories() { return categories; }

    public FileManager getSettings() { return settings; }

    public FileManager getLanguage() { return language; }

    public void initSubCommands() {
        PluginCommand collectionCommand = Bukkit.getPluginCommand("collections");
        subCommands.add(new OpenCollectionsCommand());
        new CommandManager(collectionCommand, subCommands);
    }

    private void registerEvents() {

    }
}
