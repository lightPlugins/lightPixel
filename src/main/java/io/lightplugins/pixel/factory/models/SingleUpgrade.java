package io.lightplugins.pixel.factory.models;

import java.util.List;

public class SingleUpgrade {

    private int level;
    private int data;
    private List<String> requirements;
    private List<String> costs;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public List<String> getCosts() {
        return costs;
    }

    public void setCosts(List<String> costs) {
        this.costs = costs;
    }
}
