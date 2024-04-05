package io.lightplugins.pixel.regen.abstracts;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegenAbstract {

    public Block block;
    public Location location;
    public int timer;

    public Block getType() {
        return block;
    }

    public void setType(Block block) {
        this.block = block;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

}
