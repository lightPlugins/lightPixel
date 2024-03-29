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

        LightEco.instance.getQueryManager().prepareNewAccount(uuid)
                .thenAccept(result -> {
                    if(result) {
                        LightEconomy.api.getMessageSender().sendPlayerMessage("Successfully created account for <green>" + uuid, event.getPlayer());
                        return;
                    }
                    LightEconomy.api.getMessageSender().sendPlayerMessage("Failed to create account for <red>" + uuid, event.getPlayer());
                });
    }
}
