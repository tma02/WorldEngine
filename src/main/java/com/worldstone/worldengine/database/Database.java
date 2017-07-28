package com.worldstone.worldengine.database;

import com.worldstone.worldengine.game.player.Player;

import java.sql.*;

public class Database {

    private String jdbcURL;
    private String username;
    private String password;

    public Database(String host, int port, String database, String username, String password) {
        this.jdbcURL = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        this.username = username;
        this.password = password;
    }

    public Player loadPlayer(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `players` WHERE 'username' = ?;");
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();

        Player player = new Player();
        results.getString("username");
        results.getString("display_name");
        results.getString("email");
        results.getString("inventory");
        results.getString("area");
        results.getString("skill_exp_map");
        return player;
    }

}
