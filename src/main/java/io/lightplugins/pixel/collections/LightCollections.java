package io.lightplugins.pixel.collections;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.commands.OpenCollectionsCommand;
import io.lightplugins.pixel.collections.config.MessageParams;
import io.lightplugins.pixel.collections.config.SettingParams;
import io.lightplugins.pixel.collections.events.CreatePlayerCollectionData;
import io.lightplugins.pixel.collections.models.PlayerCollectionData;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.CommandManager;
import io.lightplugins.pixel.util.manager.FileManager;
import io.lightplugins.pixel.util.manager.MultiFileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LightCollections implements LightModule {

    public static LightCollections instance;
    public boolean isModuleEnabled = false;

    public final String moduleName = "collections";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    public static List<File> collectionFiles = new ArrayList<>();
    public static HashMap<String, List<File>> collectionFilesMap = new HashMap<>();
    public static HashMap<UUID, List<PlayerCollectionData>> playerCollectionData = new HashMap<>();

    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;
    private FileManager categories;
    private MultiFileManager multiFileManager;



    @Override
    public void enable() {

        instance = this;
        initFiles();
        this.settingParams = new SettingParams(this);
        selectLanguage();   // must be called before messageParams = new ...
        messageParams = new MessageParams(language);
        readCollectionMenuItems();
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

    private void readCollectionMenuItems() {
        try {
            this.multiFileManager = new MultiFileManager(
                    "plugins/lightPixel/" + moduleName + "/collections/");
            collectionFiles = multiFileManager.getFiles();
            Light.getDebugPrinting().print(
                    "Found §e" + collectionFiles.size() + "§f collection file(s)");
            if(collectionFiles.size() > 0) {
                collectionFiles.forEach(singleFile -> {
                    FileConfiguration conf = YamlConfiguration.loadConfiguration(singleFile);
                    String collectionCategory = conf.getString("category");
                    List<File> filesInCategory = collectionFilesMap.getOrDefault(collectionCategory, new ArrayList<>());
                    filesInCategory.add(singleFile);
                    collectionFilesMap.put(collectionCategory, filesInCategory);
                    Light.getDebugPrinting().print("- §e" + singleFile.getName());
                });
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to initialize collection configs!", ex);
        }
    }

    public List<File> getCollectionByCategory(String key) {
        List<File> files = new ArrayList<>();
        for (Map.Entry<String, List<File>> entry : collectionFilesMap.entrySet()) {
            if (entry.getKey().contains(key)) {
                files.addAll(entry.getValue());
            }
        }
        return files;
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
        Bukkit.getPluginManager().registerEvents(new CreatePlayerCollectionData(), Light.instance);
    }
}
