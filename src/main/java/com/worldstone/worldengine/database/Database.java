package com.worldstone.worldengine.database;

import com.google.gson.Gson;
import com.worldstone.worldengine.game.item.Item;
import com.worldstone.worldengine.game.player.PlayerCharacter;

import java.sql.*;
import java.util.List;

public class Database {

    private String jdbcURL;
    private String username;
    private String password;

    public Database(String host, int port, String database, String username, String password) {
        this.jdbcURL = String.format("jdbc:postgresql://%s:%d/%s?sslmode=require", host, port, database);
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

    public User loadUser(String email) throws Exception {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?;");
        statement.setString(1, email);
        ResultSet results = statement.executeQuery();
        if (!results.next()) {
            throw new Exception("User does not exist");
        }
        connection.close();
        return new User(email, results.getString("password"), this.loadCharacterList(email));
    }

    public List<String> loadCharacterList(String email) throws Exception {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?;");
        statement.setString(1, email);
        ResultSet results = statement.executeQuery();
        if (!results.next()) {
            throw new Exception("User does not exist");
        }
        connection.close();

        return new Gson().fromJson(results.getString("character_list"), List.class);
    }

    public PlayerCharacter loadPlayerCharacter(User user, String displayName) throws Exception {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM characters WHERE display_name = ? AND email = ?;");
        statement.setString(1, displayName);
        statement.setString(2, user.getEmail());
        ResultSet results = statement.executeQuery();
        if (!results.next()) {
            throw new Exception("User does not exist");
        }
        connection.close();

        PlayerCharacter playerCharacter = new PlayerCharacter(user, displayName);

        String invStr = results.getString("inventory");
        List<String> items = new Gson().fromJson(invStr, List.class);
        Gson itemGson = new Gson();
        for (String itemStr : items) {
            Item item = itemGson.fromJson(itemStr, Item.class);
            playerCharacter.getInventory().add(item);
        }

        String areaStr = results.getString("area");
        playerCharacter.setArea(areaStr);

        String skillExpMapStr = results.getString("skill_exp_map");
        return playerCharacter;
    }

}
