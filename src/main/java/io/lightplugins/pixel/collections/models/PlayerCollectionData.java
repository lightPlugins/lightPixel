package io.lightplugins.pixel.collections.models;

import java.util.UUID;

public class PlayerCollectionData {

    private final UUID playerUUID; // maybe optional via HashMap
    private final Collection collection;
    private int currentCollectionPoints;
    private int neededCollectionPoints;
    private int currentCollectionLevel;
    private int maxCollectionLevel;

    public PlayerCollectionData(UUID playerUUID, Collection collection) {
        this.playerUUID = playerUUID;
        this.collection = collection;

        initNeededCollectionPoints();
        initMaxCollectionLevel();
    }

    private void initNeededCollectionPoints() {
        this.neededCollectionPoints = collection.getCollectionLevels().get(currentCollectionLevel).getAmountToFarm();
    }

    private void initMaxCollectionLevel() {
        this.maxCollectionLevel = collection.getCollectionLevels().size();
    }

    public void addCollectionPoints(int points) {
        this.currentCollectionPoints += points;
    }
    public void removeCollectionPoints(int points) {
        this.currentCollectionPoints -= points;
    }
    public void addCollectionLevel(int amount) {
        this.currentCollectionLevel += amount;
    }
    public void removeCollectionLevel(int level) {
        this.currentCollectionLevel -= level;
    }
    public UUID getPlayerUUID() {
        return playerUUID;
    }
    public Collection getCollections() {
        return collection;
    }
    public int getCurrentCollectionPoints() {
        return currentCollectionPoints;
    }
    public int getCurrentLevel() { return currentCollectionLevel; }
    public int getNeededCollectionPoints() { return neededCollectionPoints; }
    public int getMaxCollectionLevel() { return maxCollectionLevel; }


}
