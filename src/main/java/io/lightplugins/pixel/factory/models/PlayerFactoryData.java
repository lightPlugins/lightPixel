package io.lightplugins.pixel.factory.models;

import java.util.UUID;

public class PlayerFactoryData {

    private final UUID playerUUID;      // This is the UUID of the player
    private final String factoryID;     // This is the file name in /factories/ folder
    private long allTime = 0;
    private long storage = 0;
    private int speed = 0;
    private int fortune = 0;
    private int efficiency = 0;
    private int capacity = 0;
    private int milestoneLevel = 0;

    public PlayerFactoryData(UUID playerUUID, String factoryID) {
        this.playerUUID = playerUUID;
        this.factoryID = factoryID;
    }


    public String getFactoryID() {
        return factoryID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }




    public long getAllTime() {
        return allTime;
    }

    public void addAllTime(long time) {
        this.allTime += time;
    }

    public void setAllTime(long allTime) {
        this.allTime = allTime;
    }

    public long getStorage() {
        return storage;
    }

    public void addStorage(long storage) {
        this.storage += storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFortune() {
        return fortune;
    }

    public void setFortune(int fortune) {
        this.fortune = fortune;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMilestoneLevel() {
        return milestoneLevel;
    }

    public void addMilestoneLevel(int milestoneLevel) {
        this.milestoneLevel += milestoneLevel;
    }

    public void setMilestoneLevel(int milestoneLevel) {
        this.milestoneLevel = milestoneLevel;
    }

}
