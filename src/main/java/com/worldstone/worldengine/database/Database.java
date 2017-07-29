package com.worldstone.worldengine.database;

import com.google.gson.Gson;
import com.worldstone.worldengine.game.player.PlayerCharacter;

import java.sql.*;
import java.util.List;

public class Database {

    private String jdbcURL;
    private String username;
    private String password;

    public Database(String host, int port, String database, String username, String password) {
        this.jdbcURL = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        this.username = username;
        this.password = password;
    }

    public boolean testConnection() {
        try {
            Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> loadCharacterList(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE 'username' = ?;");
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();
        results.first();
        connection.close();
        return new Gson().fromJson(results.getString("character_list"), List.class);
    }

    public PlayerCharacter loadPlayerCharacter(String displayName) throws SQLException {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `characters` WHERE 'display_name' = ?;");
        statement.setString(1, displayName);
        ResultSet results = statement.executeQuery();
        results.first();
        connection.close();

        PlayerCharacter playerCharacter = new PlayerCharacter();
        results.getString("display_name");
        results.getString("inventory");
        results.getString("area");
        results.getString("skill_exp_map");
        return playerCharacter;
    }

}
