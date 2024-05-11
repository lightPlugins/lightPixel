package io.lightplugins.pixel.compactor;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.compactor.config.MessageParams;
import io.lightplugins.pixel.compactor.config.SettingParams;
import io.lightplugins.pixel.compactor.models.Compactor;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LightCompactor implements LightModule {

    public static LightCompactor instance;
    public boolean isModuleEnabled = false;

    public final String moduleName = "compactor";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public static HashMap<UUID, Compactor> compactor2000 = new HashMap<>();
    public static HashMap<UUID, Compactor> compactor4000 = new HashMap<>();
    public static HashMap<UUID, Compactor> compactor6000 = new HashMap<>();

    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;


    @Override
    public void enable() {
        instance = this;
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
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    private void initFiles() {
        this.settings = new FileManager(
                Light.instance, moduleName + "/settings.yml", true);
    }

    private void selectLanguage() {
        this.language = Light.instance.selectLanguage(settingParams.getModuleLanguage(), moduleName);
    }

    private void initSubCommands() {
        //PluginCommand ecoCommand = Bukkit.getPluginCommand("skills");
        //subCommands.add(new DummyCommand());
        //new CommandManager(ecoCommand, subCommands);

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

}
