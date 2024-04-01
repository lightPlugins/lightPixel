package io.lightplugins.economy.eco.implementer.events;

import io.lightplugins.economy.LightEconomy;
import io.lightplugins.economy.eco.LightEco;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class CreatePlayerOnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(BlockBreakEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        LightEco.economyVaultyService.createPlayerAccountAsync(uuid).thenAcceptAsync(result -> {
            if(result) {
                //LightEconomy.getMessageSender().sendPlayerMessage("Successfully created account for <green>" + uuid, event.getPlayer());
            } else {
                //LightEconomy.getMessageSender().sendPlayerMessage("Failed to create account for <red>" + uuid, event.getPlayer());
            }
        })
        .exceptionally(ex -> {
            ex.printStackTrace();
            //LightEconomy.getMessageSender().sendPlayerMessage("Failed to create account for <red>" + uuid, event.getPlayer());
            return null;
        });
    }
}
