package io.lightplugins.pixel.collections.models;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Levels {

    private final FileConfiguration conf;
    private final String path;
    private final String type;

    private int amountToFarm;
    private List<String> rewards = new ArrayList<>();
    private List<String> actions = new ArrayList<>();

    public Levels(File file, String path) {
        this.type = file.getName().replace(".yml", "");
        this.conf = YamlConfiguration.loadConfiguration(file);
        this.path = path;

        initRewardList();
        initActionList();
        setAmountToFarm();

    }

    private void initRewardList() {
        rewards.addAll(conf.getStringList("levels." + path + ".rewards"));
    }

    private void initActionList() {
        actions.addAll(conf.getStringList("levels." + path + ".actions"));
    }

    private void setAmountToFarm() {
        amountToFarm = conf.getInt("levels." + path + ".amount-to-farm");
    }

    public FileConfiguration getConf() {
        return conf;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public int getAmountToFarm() {
        return amountToFarm;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public List<String> getActions() {
        return actions;
    }




}
