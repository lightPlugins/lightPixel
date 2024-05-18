package io.lightplugins.pixel.profiles;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.profiles.api.LightProfilesAPI;
import io.lightplugins.pixel.profiles.config.MessageParams;
import io.lightplugins.pixel.profiles.config.SettingParams;
import io.lightplugins.pixel.profiles.models.Profile;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.FileManager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LightProfiles implements LightModule {


    public static LightProfiles instance;
    public static LightProfilesAPI lightProfilesAPI;
    public boolean isModuleEnabled = false;

    public final String moduleName = "profiles";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();



    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;

    @Override
    public void enable() {

        instance = this;
        lightProfilesAPI = new LightProfilesAPI();
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
        return moduleName;
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
