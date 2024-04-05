package io.lightplugins.pixel.regen;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.regen.abstracts.RegenAbstract;
import io.lightplugins.pixel.regen.config.MessageParams;
import io.lightplugins.pixel.regen.config.SettingParams;
import io.lightplugins.pixel.regen.events.BlockBreak;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.FileManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class LightRegen implements LightModule {

    public static LightRegen instance;
    public boolean isModuleEnabled = false;

    public final String moduleName = "regen";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final List<RegenAbstract> regenList = new ArrayList<>();

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
                "Successfully started core module " + this.moduleName + "!");
    }

    @Override
    public void disable() {

    }

    @Override
    public void reload() {
        //initFiles();
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
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), Light.instance);
    }

    public SettingParams getSettingParams() {
        return settingParams;
    }

    public static MessageParams getMessageParams() {
        return messageParams;
    }

    public FileManager getSettings() { return settings; }

    public FileManager getLanguage() { return language; }

    public List<RegenAbstract> getRegenList() { return regenList; }
}
