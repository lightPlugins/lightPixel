package io.lightplugins.pixel.collections.events;

import io.lightplugins.pixel.Light;
import io.lightplugins.pixel.collections.LightCollections;
import io.lightplugins.pixel.collections.models.Collection;
import io.lightplugins.pixel.collections.models.PlayerCollectionData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreatePlayerCollectionData implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        List<PlayerCollectionData> playerCollections =
                LightCollections.playerCollectionData.getOrDefault(uuid, new ArrayList<>());

        Light.getDebugPrinting().print("Player " + player.getName() + " joined the server");

        for (Map.Entry<String, List<File>> entry : LightCollections.collectionFilesMap.entrySet()) {
            Light.getDebugPrinting().print("Checking collection category " + entry.getKey());
            String collectionCategory = entry.getKey();
            boolean collectionExists = false;
            Light.getDebugPrinting().print("Collection Category: " + collectionCategory);


            for (PlayerCollectionData playerCollection : playerCollections) {
                if (playerCollection.getCollections().getCategory().equals(collectionCategory)) {
                    Light.getDebugPrinting().print("Collection is already existing: " + collectionCategory);
                    collectionExists = true;
                    break;
                }
            }

            if (!collectionExists) {
                // Die Collection fehlt in der Liste des Spielers, also fügen wir sie hinzu
                Light.getDebugPrinting().print("Start adding category " + collectionCategory);
                for (File file : entry.getValue()) {
                    Light.getDebugPrinting().print("Adding collection " +
                            file.getName().replace(".yml", ""));
                    Collection collection = new Collection(file);
                    PlayerCollectionData newPlayerCollection = new PlayerCollectionData(uuid, collection);
                    playerCollections.add(newPlayerCollection);
                }
            }
        }

        Light.getDebugPrinting().print("Player " + player.getName() + " has now " + playerCollections.size() + " collections.");
        LightCollections.playerCollectionData.put(uuid, playerCollections);

        Light.getDebugPrinting().print("Starting messaging player " + player.getName() + " about his collections");

        for(PlayerCollectionData data : playerCollections) {
            Light.getDebugPrinting().print("playerCollectionData: "
                    + data.getPlayerUUID() + " "
                    + data.getCollections().getCollection() + " "
                    + data.getCollections().getCategory());
            player.sendMessage("§7Collection: §c" + data.getCollections().getCollection() + " §7Category: §c" + data.getCollections().getCategory());
            player.sendMessage("§7Max Levels: §c" + data.getMaxCollectionLevel());
            player.sendMessage("§7Current Level: §c" + data.getCurrentLevel());
            player.sendMessage("§7Current Collection Points: §c" + data.getCurrentCollectionPoints());
            player.sendMessage("§7UUID: §c" + data.getPlayerUUID());
        }

        Light.getDebugPrinting().print("Finished messaging player " + player.getName() + " about his collections");
    }
}
