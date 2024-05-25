package io.lightplugins.pixel.factory.models;

import java.util.ArrayList;
import java.util.List;

public class FactoriesFromConfig {

    private final List<FactoryFromConfig> factoryList = new ArrayList<>();

    public void addFactory(FactoryFromConfig factory) {

        if(!factoryList.contains(factory)) {
            factoryList.add(factory);
        }
    }

    public List<FactoryFromConfig> getFactoryList() {
        return factoryList;
    }
}
