package io.lightplugins.pixel.factory.models;

import com.willfp.eco.core.items.Items;
import io.lightplugins.pixel.Light;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class FactoryFromConfig {

    private final FileConfiguration conf;
    private ConfigurationSection FACTORY_SECTION;
    private ConfigurationSection UPGRADES_SECTION;
    private String factoryID;    // The ID coming from the config file name
    private String category;     // The category of the factory coming from config
    private boolean enabled;     // If the factory is enabled
    private String permission;   // The permission needed to open the factory
    private String requiredPrevious; // The previous factory needed to open this factory
    private String itemToGen;    // The item that the factory generates
    private int startSpeed;      // The starting speed of the factory
    private int startAmount;     // The starting amount of the factory
    private int startCapacity;   // The starting capacity of the factory
    private Upgrades factoryUpgrades; // The upgrades for the factory
    private ItemStack genItemStack; // The itemstack of the item to generate;

    public FactoryFromConfig(File file) {

        this.conf = YamlConfiguration.loadConfiguration(file);

        this.FACTORY_SECTION = conf.getConfigurationSection("factory");
        this.UPGRADES_SECTION = conf.getConfigurationSection("factory.upgrades");

        this.factoryID = file.getName().replace(".yml", "");
        this.category = FACTORY_SECTION.getString("category");
        this.enabled = FACTORY_SECTION.getBoolean("enabled");
        this.permission = FACTORY_SECTION.getString("permission");
        this.requiredPrevious = FACTORY_SECTION.getString("required-previous");
        this.itemToGen = FACTORY_SECTION.getString("item-to-gen");
        this.startSpeed = FACTORY_SECTION.getInt("start-speed");
        this.startAmount = FACTORY_SECTION.getInt("start-amount");
        this.startCapacity = FACTORY_SECTION.getInt("start-capacity");

        initFactoryUpgrades();
        initItemStack();

    }

    private void initFactoryUpgrades() {
        if(UPGRADES_SECTION == null) {
            Light.getDebugPrinting().print("No upgrades found for factory " + factoryID);
            return;
        }

        this.factoryUpgrades = new Upgrades(UPGRADES_SECTION);

    }

    private void initItemStack() {

        if(itemToGen.contains("ecoitems:")) {
            String[] split = itemToGen.split(":");
            this.genItemStack = Items.lookup(split[1]).getItem();
        } else {
            ItemStack is = new ItemStack(Material.STONE);
            try {
                this.genItemStack = new ItemStack(Material.valueOf(itemToGen.toUpperCase()));
            } catch (NullPointerException exception) {
                Light.getDebugPrinting().print("Invalid material for factory " + factoryID);
                this.genItemStack = is;
            }
        }
    }

    public FileConfiguration getConf() {
        return conf;
    }

    public ConfigurationSection getFACTORY_SECTION() {
        return FACTORY_SECTION;
    }

    public void setFACTORY_SECTION(ConfigurationSection FACTORY_SECTION) {
        this.FACTORY_SECTION = FACTORY_SECTION;
    }

    public ConfigurationSection getUPGRADES_SECTION() {
        return UPGRADES_SECTION;
    }

    public void setUPGRADES_SECTION(ConfigurationSection UPGRADES_SECTION) {
        this.UPGRADES_SECTION = UPGRADES_SECTION;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRequiredPrevious() {
        return requiredPrevious;
    }

    public void setRequiredPrevious(String requiredPrevious) {
        this.requiredPrevious = requiredPrevious;
    }

    public String getItemToGen() {
        return itemToGen;
    }

    public void setItemToGen(String itemToGen) {
        this.itemToGen = itemToGen;
    }

    public int getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(int startSpeed) {
        this.startSpeed = startSpeed;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    public int getStartCapacity() {
        return startCapacity;
    }

    public void setStartCapacity(int startCapacity) {
        this.startCapacity = startCapacity;
    }

    public Upgrades getFactoryUpgrades() {
        return factoryUpgrades;
    }

    public void setFactoryUpgrades(Upgrades factoryUpgrades) {
        this.factoryUpgrades = factoryUpgrades;
    }

    public ItemStack getGenItemStack() {
        return genItemStack;
    }

    public void setGenItemStack(ItemStack genItemStack) {
        this.genItemStack = genItemStack;
    }

}
