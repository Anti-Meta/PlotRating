package nl.antimeta.plotrating.model;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLException;

public class DatabaseModel {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    public DatabaseModel(FileConfiguration config) throws SQLException, ClassNotFoundException {
        host = config.getString("host");
        port = config.getString("port");
        database = config.getString("database");
        username = config.getString("username");
        password = config.getString("password");
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
