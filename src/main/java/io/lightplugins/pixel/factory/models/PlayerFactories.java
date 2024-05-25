package io.lightplugins.pixel.factory.models;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactories {

    private final List<PlayerFactoryData> playerFactories = new ArrayList<>();

    public void addPlayerFactory(PlayerFactoryData playerFactoryData) {

        if(!playerFactories.contains(playerFactoryData)) {
            playerFactories.add(playerFactoryData);
        }
    }

    public List<PlayerFactoryData> getPlayerFactoriesData() {
        return playerFactories;
    }
}
