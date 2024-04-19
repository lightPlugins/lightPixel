package io.lightplugins.pixel.collections.placeholders;

import io.lightplugins.pixel.collections.LightCollections;
import io.lightplugins.pixel.collections.models.PlayerCollectionData;
import io.lightplugins.pixel.util.SubPlaceholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class CollectionPlaceholder extends SubPlaceholder {

    //  %lightpixel_collections_level_farming_wheat_current%
    //  %lightpixel_collections_level_farming_wheat_max%

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        String[] parts = params.split("_");
        if (parts.length != 5) {
            return null; // Ungültiger Platzhalter
        }
        String module = parts[0];
        String subModule = parts[1];
        String category = parts[2];
        String collection = parts[3];
        String param = parts[4];

        if(!module.equalsIgnoreCase("collections")) {
            return null;
        }

        if(!subModule.equalsIgnoreCase("level")) {
            return null;
        }

        //

        List<PlayerCollectionData> playerCollections = LightCollections.playerCollectionData.get(player.getUniqueId());
        if (playerCollections == null) {
            return null;
        }


        // Durchlaufen Sie die Sammlungsdaten des Spielers, um die gesuchte Sammlung zu finden
        for (PlayerCollectionData playerCollection : playerCollections) {
            if (playerCollection.getCollections().getCategory().equalsIgnoreCase(category) &&
                    playerCollection.getCollections().getCollection().equalsIgnoreCase(collection)) {
                // Wir haben die gesuchte Sammlung gefunden, also geben wir die angeforderten Daten zurück
                if(playerCollection.getCollections().getCollection().equalsIgnoreCase(collection)) {

                        //  Placeholder: %lightpixel_collections_level_<category>_<collection>_current%
                    if(param.equalsIgnoreCase("current")) {
                        return String.valueOf(playerCollection.getCurrentLevel());


                        //  Placeholder: %lightpixel_collections_level_<category>_<collection>_max%
                    } else if (param.equalsIgnoreCase("max")) {
                        return String.valueOf(playerCollection.getCollections().getCollectionLevels().size());
                    }
                }
            }
        }

        return null;
    }
}
