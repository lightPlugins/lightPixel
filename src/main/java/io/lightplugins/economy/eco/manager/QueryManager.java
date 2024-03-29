package io.lightplugins.economy.eco.manager;

//  999.999.999.999.999.999.999.999.999.999.999.999.999.999.999.999.999,99 -> max value for the database for NUMERIC(32, 2)

import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.light.api.database.SQLDatabase;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class QueryManager {

    private final String tableName = "economy_core";
    private final SQLDatabase database;

    public QueryManager(SQLDatabase database) {
        this.database = database;
    }

    public CompletableFuture<ResultSet> test() throws SQLException {
        String query = "SELECT * FROM " + tableName;
        return database.testQueryAsync(query);
    }

    public void createEcoTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(uuid varchar(36) NOT NULL UNIQUE, balance numeric(32,2), primary key (uuid))";
        database.executeSql(query);
    }

    public CompletableFuture<Boolean> prepareNewPlayer(UUID uuid) {

        BigDecimal startBalance = LightEco.instance.getSettingParams().defaultCurrency().getStartBalance();
        String query = "INSERT INTO " + tableName + "(uuid, balance) VALUES (?,?)";

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        database.executeSqlFuture(query, uuid.toString(), startBalance)
                .thenAcceptAsync(result -> {
                    if(result > 0) {
                        future.complete(true);
                        return;
                    }
                    future.complete(false);
                });

        return future;
    }
}
