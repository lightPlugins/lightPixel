package io.lightplugins.pixel.util.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.MoistureChangeEvent;

public class MoistureLevelPrepare implements Listener {

    @EventHandler
    public void onMoistureLevelPrepare(MoistureChangeEvent event) {
        if(event.getBlock().getWorld().getName().equalsIgnoreCase("wonderland")) {
            event.setCancelled(true);
        }
    }
}
