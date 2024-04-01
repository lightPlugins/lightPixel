package io.lightplugins.economy.util.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.lightplugins.economy.LightEconomy;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Connection {

    private final LightEconomy light;

    public Connection(LightEconomy light) {
        this.light = light;
    }

    public void connectMariaDB() {

        FileConfiguration config = LightEconomy.database.getConfig();

        String host = config.getString("mysql.host");
        // bungee needs an integer
        String port = config.getString("mysql.port");
        String database = config.getString("mysql.database");
        String user = config.getString("mysql.user");
        String password = config.getString("mysql.password");
        Boolean ssl = config.getBoolean("mysql.ssl");
        Boolean useServerPrepStmts = config.getBoolean("mysql.advanced.useServerPrepStmts");
        Boolean cachePrepStmts = config.getBoolean("mysql.advanced.cachePrepStmts");
        int prepStmtCacheSize = config.getInt("mysql.advanced.prepStmtCacheSize");
        int prepStmtCacheSqlLimit = config.getInt("mysql.advanced.prepStmtCacheSqlLimit");
        int connectionPoolSize = config.getInt("mysql.advanced.connectionPoolSize");

        HikariConfig hikariConfig = new HikariConfig();
        //hikariConfig.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        hikariConfig.setJdbcUrl("jdbc:mariadb://" +host + ":" + port + "/" + database);
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.addDataSourceProperty("serverName", host);
        hikariConfig.addDataSourceProperty("port", port);
        hikariConfig.addDataSourceProperty("databaseName", database);
        hikariConfig.addDataSourceProperty("user", user);
        hikariConfig.addDataSourceProperty("password", password);
        hikariConfig.addDataSourceProperty("useSSL", ssl);
        hikariConfig.setMaximumPoolSize(connectionPoolSize);
        hikariConfig.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
        hikariConfig.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
        light.ds = new HikariDataSource(hikariConfig);
        LightEconomy.getDebugPrinting().print("Connected to MariaDB");
    }

    /**
     * Connects to the SQLite database.
     */
    public void connectSQLite() {
        // Define the database file name
        String database = "lightEconomy.db";

        // Create a file object for the database in the data folder.
        File sqlFile = new File(light.getDataFolder(), database);

        // If the database file does not exist, create a new file.
        if (!sqlFile.exists()) {
            try {
                if(!sqlFile.createNewFile()) {
                    // Print an error message if the file creation fails
                    LightEconomy.getDebugPrinting().print("&cCannot create &4" + database);
                    return;
                }
            } catch (IOException e) {
                // Print an error message and stack trace if an exception occurs during file creation.
                LightEconomy.getDebugPrinting().print("&cCannot create &4" + database);
                e.printStackTrace();
            }
        } else {
            // Skip if the SQLite file already exists
            LightEconomy.getDebugPrinting().print("SQLite file already exists -> skipping");
        }

        // Configure the HikariCP connection pool for SQLite
        HikariConfig config = new HikariConfig();
        config.setPoolName("SQLite");
        config.setJdbcUrl("jdbc:sqlite:" + sqlFile.getAbsolutePath());
        config.setMaximumPoolSize(10);
        light.ds = new HikariDataSource(config);
        // Print a success message after connecting to SQLite
        LightEconomy.getDebugPrinting().print("Connected to SQLite");
    }

}
