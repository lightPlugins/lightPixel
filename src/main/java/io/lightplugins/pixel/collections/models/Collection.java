package io.lightplugins.pixel.collections.models;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Collection {

    private final FileConfiguration conf;
    private final File file;
    private final String collection;
    private final String type;
    private final String category;

    private final List<String> levelUpLore = new ArrayList<>();
    private final List<Levels> collectionLevels = new ArrayList<>();


    public Collection(File file) {
        this.file = file;
        this.conf = YamlConfiguration.loadConfiguration(file);
        this.collection = file.getName().replace(".yml", "");
        this.levelUpLore.addAll(conf.getStringList("level-up-message"));

        this.type = conf.getString("type");
        this.category = conf.getString("category");

        initLevels();

    }

    private void initLevels() {
        for(String path : conf.getConfigurationSection("levels").getKeys(false)) {
            collectionLevels.add(new Levels(file, path));
        }
    }

    public FileConfiguration getConf() {
        return conf;
    }

    public File getFile() {
        return file;
    }

    public String getCollection() {
        return collection;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getLevelUpLore() {
        return levelUpLore;
    }

    public List<Levels> getCollectionLevels() {
        return collectionLevels;
    }


}
