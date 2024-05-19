package io.lightplugins.pixel.factory.models;

import io.lightplugins.pixel.Light;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Upgrades {

    private String factoryID;

    private final ConfigurationSection SPEED_LEVELS;
    private final ConfigurationSection FORTUNE_LEVELS;
    private final ConfigurationSection EFFICIENCY_LEVELS;
    private final ConfigurationSection CAPACITY_LEVELS;

    private List<SingleUpgrade> speedUpgrades = new ArrayList<>();
    private List<SingleUpgrade> fortuneUpgrades = new ArrayList<>();
    private List<SingleUpgrade> efficiencyUpgrades = new ArrayList<>();
    private List<SingleUpgrade> capacityUpgrades = new ArrayList<>();

    public Upgrades (ConfigurationSection section) {
        this.SPEED_LEVELS = section.getConfigurationSection("speed");
        this.FORTUNE_LEVELS = section.getConfigurationSection("fortune");
        this.EFFICIENCY_LEVELS = section.getConfigurationSection("efficiency");
        this.CAPACITY_LEVELS = section.getConfigurationSection("capacity");

        initSpeedUpgrades();
        initFortuneUpgrades();
        initEfficiencyUpgrades();
        initCapacityUpgrades();

    }

    private void initSpeedUpgrades() {
        if(SPEED_LEVELS == null) {
            Light.getDebugPrinting().print("No speed upgrades found for factory " + factoryID);
            return;
        }

        for(String level : SPEED_LEVELS.getKeys(false)) {
            SingleUpgrade upgrade = new SingleUpgrade();
            upgrade.setLevel(Integer.parseInt(level));
            upgrade.setData(SPEED_LEVELS.getInt(level + ".data"));
            upgrade.setRequirements(SPEED_LEVELS.getStringList(level + ".requirements"));
            upgrade.setCosts(SPEED_LEVELS.getStringList(level + ".costs"));
            speedUpgrades.add(upgrade);
        }
    }

    private void initFortuneUpgrades() {
        if(FORTUNE_LEVELS == null) {
            Light.getDebugPrinting().print("No fortune upgrades found for factory " + factoryID);
            return;
        }

        for(String level : FORTUNE_LEVELS.getKeys(false)) {
            SingleUpgrade upgrade = new SingleUpgrade();
            upgrade.setLevel(Integer.parseInt(level));
            upgrade.setData(FORTUNE_LEVELS.getInt(level + ".data"));
            upgrade.setRequirements(FORTUNE_LEVELS.getStringList(level + ".requirements"));
            upgrade.setCosts(FORTUNE_LEVELS.getStringList(level + ".costs"));
            fortuneUpgrades.add(upgrade);
        }
    }

    private void initEfficiencyUpgrades() {
        if(EFFICIENCY_LEVELS == null) {
            Light.getDebugPrinting().print("No efficiency upgrades found for factory " + factoryID);
            return;
        }

        for(String level : EFFICIENCY_LEVELS.getKeys(false)) {
            SingleUpgrade upgrade = new SingleUpgrade();
            upgrade.setLevel(Integer.parseInt(level));
            upgrade.setData(EFFICIENCY_LEVELS.getInt(level + ".data"));
            upgrade.setRequirements(EFFICIENCY_LEVELS.getStringList(level + ".requirements"));
            upgrade.setCosts(EFFICIENCY_LEVELS.getStringList(level + ".costs"));
            efficiencyUpgrades.add(upgrade);
        }
    }

    private void initCapacityUpgrades() {
        if(CAPACITY_LEVELS == null) {
            Light.getDebugPrinting().print("No capacity upgrades found for factory " + factoryID);
            return;
        }

        for(String level : CAPACITY_LEVELS.getKeys(false)) {
            SingleUpgrade upgrade = new SingleUpgrade();
            upgrade.setLevel(Integer.parseInt(level));
            upgrade.setData(CAPACITY_LEVELS.getInt(level + ".data"));
            upgrade.setRequirements(CAPACITY_LEVELS.getStringList(level + ".requirements"));
            upgrade.setCosts(CAPACITY_LEVELS.getStringList(level + ".costs"));
            capacityUpgrades.add(upgrade);
        }
    }

    public List<SingleUpgrade> getSpeedUpgrades() {
        return speedUpgrades;
    }

    public void setSpeedUpgrades(List<SingleUpgrade> speedUpgrades) {
        this.speedUpgrades = speedUpgrades;
    }

    public List<SingleUpgrade> getFortuneUpgrades() {
        return fortuneUpgrades;
    }

    public void setFortuneUpgrades(List<SingleUpgrade> fortuneUpgrades) {
        this.fortuneUpgrades = fortuneUpgrades;
    }

    public List<SingleUpgrade> getEfficiencyUpgrades() {
        return efficiencyUpgrades;
    }

    public void setEfficiencyUpgrades(List<SingleUpgrade> efficiencyUpgrades) {
        this.efficiencyUpgrades = efficiencyUpgrades;
    }

    public List<SingleUpgrade> getCapacityUpgrades() {
        return capacityUpgrades;
    }

    public void setCapacityUpgrades(List<SingleUpgrade> capacityUpgrades) {
        this.capacityUpgrades = capacityUpgrades;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

}
