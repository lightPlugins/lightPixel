package io.lightplugins.pixel.factory.api;


import io.lightplugins.pixel.factory.LightFactory;
import io.lightplugins.pixel.factory.models.FactoryFromConfig;
import io.lightplugins.pixel.factory.models.PlayerFactories;
import io.lightplugins.pixel.factory.models.PlayerFactoryData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LightFactoryAPI {

    public List<ItemStack> getFactoryItemsByCategory(String factoryCategory) {

        List<ItemStack> list = new ArrayList<>();

        for(FactoryFromConfig factoryFromConfig : LightFactory.instance.getFactoriesFromConfig().getFactoryList()) {
            if(factoryFromConfig.getCategory().equals(factoryCategory)) {
                list.add(factoryFromConfig.getGenItemStack());
            }
        }

        return list;
    }

    public ItemStack getFactoryItemByFactoryID(String factoryID) {

        for(FactoryFromConfig factoryFromConfig : LightFactory.instance.getFactoriesFromConfig().getFactoryList()) {
            if(factoryFromConfig.getFactoryID().equalsIgnoreCase(factoryID)) {
                return factoryFromConfig.getGenItemStack();
            }
        }
        return new ItemStack(Material.STONE);
    }


    public HashMap<UUID, PlayerFactories> getAllPlayerFactoriesList() {
        return LightFactory.instance.getPlayerFactories();
    }

    public List<PlayerFactoryData> getPlayerFactoriesByUUID(UUID playerUUID) {
        return LightFactory.instance.getPlayerFactories().get(playerUUID).getPlayerFactoriesData();
    }

    public PlayerFactoryData getPlayerFactoryDataByID(UUID playerUUID, String factoryID) {
        for(PlayerFactoryData playerFactoryData : getPlayerFactoriesByUUID(playerUUID)) {
            if(playerFactoryData.getFactoryID().equals(factoryID)) {
                return playerFactoryData;
            }
        }
        return null;    // factory ID isn't found.
    }


}
