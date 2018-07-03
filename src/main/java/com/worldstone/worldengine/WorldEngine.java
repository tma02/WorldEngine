package com.worldstone.worldengine;

import com.google.gson.Gson;
import com.worldstone.worldengine.database.Database;
import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.UserSocketIOListener;
import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.script.ScriptController;
import com.worldstone.worldengine.trigger.TriggerController;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class WorldEngine {

    public static WorldEngine INSTANCE;

    private Config config;
    private Game game;
    private Database database;
    private SocketServer loginServer;

    public static void main(String[] args) {
        String configPath = "config.json";
        if (args.length > 0) {
            configPath = args[0];
        }
        WorldEngine.INSTANCE = new WorldEngine(new File(configPath));
        WorldEngine.INSTANCE.init();
    }

    public WorldEngine(File configFile) {
        LoggerFactory.getLogger(this.getClass()).info("WorldEngine Version: " + getClass().getPackage().getImplementationVersion());
        LoggerFactory.getLogger(this.getClass()).info("Reading config: " + configFile.getName());
        try {
            this.config = new Gson().fromJson(new FileReader(configFile), Config.class);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(this.getClass()).error("Could not load config file: ");
            e.printStackTrace();
            System.exit(1);
        }
        this.game = new Game();

        LoginListener loginListener = new LoginListener();
        this.loginServer = new SocketServer(this.getConfig().getPorts()[0], loginListener);
    }

    public void init() {
        LoggerFactory.getLogger(this.getClass()).info("Connecting to database...");
        String host = (String) this.config.getDatabase().get("host");
        int port = (int) (double) this.config.getDatabase().get("port");
        String database = (String) this.config.getDatabase().get("database");
        String username = (String) this.config.getDatabase().get("username");
        String password = (String) this.config.getDatabase().get("password");
        this.database = new Database(host, port, database, username, password);
        if (!this.database.testConnection()) {
            System.exit(1);
        }

        LoggerFactory.getLogger(this.getClass()).info("Running scripts...");
        ScriptController.runScripts(new File("scripts/"));

        LoggerFactory.getLogger(this.getClass()).info("Starting game thread...");
        this.game.start();

        LoggerFactory.getLogger(this.getClass()).info("Starting Login server...");
        ((LoginListener) this.loginServer.getListener()).registerPacketActions();
        this.loginServer.start();

        TriggerController.triggerEvent("post_init");
    }

    public Game getGame() {
        return this.game;
    }

    public Database getDatabase() {
        return this.database;
    }

    public Config getConfig() {
        return this.config;
    }

}
