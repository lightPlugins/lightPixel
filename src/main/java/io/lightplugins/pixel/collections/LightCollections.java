package io.lightplugins.pixel.collections;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.config.MessageParams;
import io.lightplugins.pixel.collections.config.SettingParams;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.FileManager;

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



    @Override
    public void enable() {

        instance = this;
        initFiles();
        this.settingParams = new SettingParams(this);
        messageParams = new MessageParams(language);
        selectLanguage();
        initSubCommands();
        registerEvents();
        isModuleEnabled = true;
        Light.getDebugPrinting().print(
                "Successfully started core module " + this.moduleName + "!");

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

    public FileManager getSettings() { return settings; }

    public FileManager getLanguage() { return language; }

    public void initSubCommands() {

    }

    private void registerEvents() {

    }
}
