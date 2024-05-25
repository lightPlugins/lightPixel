package io.lightplugins.pixel.factory;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.factory.api.LightFactoryAPI;
import io.lightplugins.pixel.factory.commands.OpenFactoryCommand;
import io.lightplugins.pixel.factory.config.MessageParams;
import io.lightplugins.pixel.factory.config.SettingParams;
import io.lightplugins.pixel.factory.models.FactoriesFromConfig;
import io.lightplugins.pixel.factory.models.FactoryFromConfig;
import io.lightplugins.pixel.factory.models.PlayerFactories;
import io.lightplugins.pixel.factory.models.PlayerFactoryData;
import io.lightplugins.pixel.util.SubCommand;
import io.lightplugins.pixel.util.interfaces.LightModule;
import io.lightplugins.pixel.util.manager.CommandManager;
import io.lightplugins.pixel.util.manager.FileManager;
import io.lightplugins.pixel.util.manager.MultiFileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LightFactory implements LightModule {


    public static LightFactory instance;
    public static LightFactoryAPI lightFactoryAPI;
    public boolean isModuleEnabled = false;

    public final String moduleName = "factory";
    public final String adminPerm = "light." + moduleName + ".admin";
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final HashMap<UUID, PlayerFactories> playerFactories = new HashMap<>();

    private FactoriesFromConfig factoriesFromConfig;

    private SettingParams settingParams;
    private static MessageParams messageParams;

    private FileManager settings;
    private FileManager language;
    private FileManager mainMenu;
    private FileManager categoryMenu;
    private FileManager factoryMenu;
    private MultiFileManager factoryFiles;

    private FileManager defaultFactory;

    @Override
    public void enable() {

        instance = this;
        lightFactoryAPI = new LightFactoryAPI();
        initFiles();
        this.settingParams = new SettingParams(this);
        selectLanguage();
        messageParams = new MessageParams(language);
        initSubCommands();
        registerEvents();
        this.factoriesFromConfig = new FactoriesFromConfig();
        initFactories();
        isModuleEnabled = true;
        Light.getDebugPrinting().print(
                "Successfully started module " + this.moduleName + "!");

        /*

         DEBUG STUFF - show all data from all factories

        Light.getDebugPrinting().print("-----------------------------------------------");

        for (Factory factory : factories.getFactoryList()) {
            Light.getDebugPrinting().print("Factory: " + factory.getFactoryID());
            Light.getDebugPrinting().print("Category: " + factory.getCategory());
            Light.getDebugPrinting().print("Enabled: " + factory.isEnabled());
            Light.getDebugPrinting().print("Permission: " + factory.getPermission());
            Light.getDebugPrinting().print("Required Previous: " + factory.getRequiredPrevious());
            Light.getDebugPrinting().print("Item to Generate: " + factory.getItemToGen());
            Light.getDebugPrinting().print("Start Speed: " + factory.getStartSpeed());
            Light.getDebugPrinting().print("Start Amount: " + factory.getStartAmount());
            Light.getDebugPrinting().print("Start Capacity: " + factory.getStartCapacity());

            Light.getDebugPrinting().print("-----------------------------------------------");
            Light.getDebugPrinting().print("Capacity Upgrades:");
            for (SingleUpgrade upgrade : factory.getFactoryUpgrades().getCapacityUpgrades()) {
                Light.getDebugPrinting().print("Upgrade Level: " + upgrade.getLevel());
                Light.getDebugPrinting().print("Upgrade Data: " + upgrade.getData());
                Light.getDebugPrinting().print("Upgrade Requirements: " + upgrade.getRequirements());
                Light.getDebugPrinting().print("Upgrade Costs: " + upgrade.getCosts());
            }

            Light.getDebugPrinting().print("-----------------------------------------------");
            Light.getDebugPrinting().print("Efficiency Upgrades:");
            for (SingleUpgrade upgrade : factory.getFactoryUpgrades().getEfficiencyUpgrades()) {
                Light.getDebugPrinting().print("Upgrade Level: " + upgrade.getLevel());
                Light.getDebugPrinting().print("Upgrade Data: " + upgrade.getData());
                Light.getDebugPrinting().print("Upgrade Requirements: " + upgrade.getRequirements());
                Light.getDebugPrinting().print("Upgrade Costs: " + upgrade.getCosts());
            }

            Light.getDebugPrinting().print("-----------------------------------------------");
            Light.getDebugPrinting().print("Fortune Upgrades:");
            for (SingleUpgrade upgrade : factory.getFactoryUpgrades().getFortuneUpgrades()) {
                Light.getDebugPrinting().print("Upgrade Level: " + upgrade.getLevel());
                Light.getDebugPrinting().print("Upgrade Data: " + upgrade.getData());
                Light.getDebugPrinting().print("Upgrade Requirements: " + upgrade.getRequirements());
                Light.getDebugPrinting().print("Upgrade Costs: " + upgrade.getCosts());
            }

            Light.getDebugPrinting().print("-----------------------------------------------");
            Light.getDebugPrinting().print("Speed Upgrades:");
            for (SingleUpgrade upgrade : factory.getFactoryUpgrades().getSpeedUpgrades()) {
                Light.getDebugPrinting().print("Upgrade Level: " + upgrade.getLevel());
                Light.getDebugPrinting().print("Upgrade Data: " + upgrade.getData());
                Light.getDebugPrinting().print("Upgrade Requirements: " + upgrade.getRequirements());
                Light.getDebugPrinting().print("Upgrade Costs: " + upgrade.getCosts());
            }
        }
         */

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

        this.mainMenu = new FileManager(
                Light.instance, moduleName + "/inventories/main-menu.yml", true);
        this.categoryMenu = new FileManager(
                Light.instance, moduleName + "/inventories/category-menu.yml", true);
        this.factoryMenu = new FileManager(
                Light.instance, moduleName + "/inventories/factory-menu.yml", true);
        this.settings = new FileManager(
                Light.instance, moduleName + "/settings.yml", true);

        // generate default factories;

        this.defaultFactory = new FileManager(
                Light.instance, moduleName + "/factories/mining/cobblestone.yml", false);


        try {

            this.factoryFiles = new MultiFileManager(
                    "plugins/lightPixel/" + moduleName + "/factories/");

        } catch (IOException e) {
            throw new RuntimeException("Error while initialize config files", e);
        }
    }

    private void selectLanguage() {
        this.language = Light.instance.selectLanguage(settingParams.getModuleLanguage(), moduleName);
    }

    private void initSubCommands() {
        PluginCommand factoryCommand = Bukkit.getPluginCommand("factory");
        subCommands.add(new OpenFactoryCommand());
        new CommandManager(factoryCommand, subCommands);

    }

    private void initFactories() {

        long start = System.currentTimeMillis();

        Light.getDebugPrinting().print("Loading factories ...");

        if(factoryFiles.getFiles().size() == 0 ) {
            Light.getDebugPrinting().print("§cNo factory files found. Please check your configuration.");
            disable();  // disable this module if no factories are found (logic xd)
            return;
        }

        Light.getDebugPrinting().print("Found §e" + factoryFiles.getFiles().size() + "§r Factories");
        for(File file : factoryFiles.getFiles()) {
            Light.getDebugPrinting().print("- §e" + file.getName());
            FactoryFromConfig factory = new FactoryFromConfig(file);
            factoriesFromConfig.addFactory(factory);
        }

        long stop = System.currentTimeMillis();
        long took = stop - start;
        Light.getDebugPrinting().print("Loaded factories in §e" + took + "§rms");
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
    public FileManager getCategoryMenu() { return categoryMenu; }
    public FileManager getFactoryMenu() { return factoryMenu; }

    public HashMap<UUID, PlayerFactories> getPlayerFactories() {
        return playerFactories;
    }
    public FactoriesFromConfig getFactoriesFromConfig() {
        return factoriesFromConfig;
    }

    public LightFactoryAPI getLightFactoryAPI() {
        return lightFactoryAPI;
    }
}
