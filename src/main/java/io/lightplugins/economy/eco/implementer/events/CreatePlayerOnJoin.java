package io.lightplugins.economy.eco.implementer.events;

import io.lightplugins.economy.eco.LightEco;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
public class CreatePlayerOnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(BlockBreakEvent event) {
        long startTime = System.currentTimeMillis(); // Startzeit messen
        AtomicInteger counter = new AtomicInteger(0); // Zähler für abgeschlossene Vorgänge

        int amount = 3000;

        for (int i = 0; i < amount; i++) {
            Player player = event.getPlayer();
            UUID uuid = UUID.randomUUID();

            LightEco.instance.getQueryManager().prepareNewPlayer(uuid)
                    .thenAccept(result -> {
                        // Hier kannst du weitere Aktionen ausführen, falls benötigt
                        if (counter.incrementAndGet() == amount) { // Überprüfen, ob alle Vorgänge abgeschlossen sind
                            long endTime = System.currentTimeMillis(); // Endzeit messen
                            long totalTime = endTime - startTime; // Gesamtdauer berechnen
                            System.out.println("Dauer der " + amount + " Vorgänge: " + totalTime + "ms");
                        }
                    });
        }
    }
    @EventHandler
    public void onTest(AsyncPlayerChatEvent event) throws SQLException {
        event.getPlayer().sendMessage("Start");
        LightEco.instance.getQueryManager().test().thenAcceptAsync(resultSet -> {
            int rowCount;
            try {
                rowCount = getRowCount(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            event.getPlayer().sendMessage("End mit Zeilen");
        });

    }

    public int getRowCount(ResultSet resultSet) throws SQLException {
        int rowCount = 0;

        while (resultSet.next()) {
            rowCount++;
        }

        return rowCount;
    }

}
