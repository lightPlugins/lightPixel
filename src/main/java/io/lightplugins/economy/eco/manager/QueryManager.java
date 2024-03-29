package io.lightplugins.economy.eco.manager;

//  999.999.999.999.999.999.999.999.999.999.999.999.999.999.999.999.999,99 -> max value for the database for NUMERIC(32, 2)

import io.lightplugins.economy.eco.LightEco;
import io.lightplugins.economy.eco.interfaces.AccountHolder;
import io.lightplugins.light.api.database.SQLDatabase;
import io.lightplugins.light.api.util.NumberFormatter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class QueryManager {

    private final String tableName = "economy_core";
    private final SQLDatabase database;

    public QueryManager(SQLDatabase database) {
        this.database = database;
    }

    public CompletableFuture<ResultSet> test() {
        String query = "SELECT * FROM " + tableName;
        return database.executeQueryAsync(query);
    }

    public void createEcoTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(uuid varchar(36) NOT NULL UNIQUE, balance numeric(32,2), primary key (uuid))";
        database.executeSql(query);
    }

    public CompletableFuture<Boolean> prepareNewAccount(UUID uuid) {
        BigDecimal startBalance = LightEco.instance.getSettingParams().defaultCurrency().getStartBalance();
        String query = "INSERT INTO " + tableName + "(uuid, balance) VALUES (?,?)";

        return database.executeSqlFuture(query, uuid.toString(), startBalance)
                .thenApplyAsync(result -> result > 0)
                .exceptionally(ex -> {
                    throw new RuntimeException("Failed to prepare new account for UUID: " + uuid, ex);
                });
    }

    public CompletableFuture<AccountHolder> getAccountHolder(UUID uuid) {
        String query = "SELECT * FROM " + tableName + " WHERE uuid = ?";

        return database.executeQueryAsync(query, uuid.toString())
                .thenApplyAsync(resultSet -> {
                    if (resultSet == null) {
                        return null;
                    }

                    try {
                        if (resultSet.next()) {
                            double balance = resultSet.getDouble("balance");
                            AccountHolder accountHolder = new AccountHolder();
                            accountHolder.setUuid(uuid);
                            accountHolder.setBalance(NumberFormatter.formatBigDecimal(BigDecimal.valueOf(balance)));
                            return accountHolder;
                        } else {
                            return null;
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException("Error while processing result set", e);
                    }
                });
    }

    public CompletableFuture<Integer> setBalanceFromAccount(UUID uuid, BigDecimal balance) {
        String sql = "UPDATE " + tableName + " SET balance = ? WHERE uuid = ?";
        return database.executeSqlFuture(sql, balance, uuid.toString());
    }

    public CompletableFuture<Boolean> withdrawFromAccount(UUID uuid, BigDecimal amount) {
        String sql = "UPDATE " + tableName + " SET balance = balance - ? WHERE uuid = ?";
        return database.executeSqlFuture(sql, amount, uuid.toString())
                .thenApplyAsync(rowsUpdated -> rowsUpdated > 0);
    }

    public CompletableFuture<Boolean> depositFromAccount(UUID uuid, BigDecimal amount) {
        String sql = "UPDATE " + tableName + " SET balance = balance + ? WHERE uuid = ?";
        return database.executeSqlFuture(sql, amount, uuid.toString())
                .thenApplyAsync(rowsUpdated -> rowsUpdated > 0);
    }
}
