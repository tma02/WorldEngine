package com.worldstone.worldengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.worldstone.worldengine.database.Database;
import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.net.listener.PacketSocketIOListener;
import com.worldstone.worldengine.script.ScriptController;
import com.worldstone.worldengine.trigger.TriggerController;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WorldEngine {

    public static WorldEngine INSTANCE;

    private Config config;
    private Game game;
    private Database database;
    private Map<String, SocketServer> serverMap;

    public static void main(String[] args) {
        String configPath = "config.json";
        if (args.length > 0) {
            configPath = args[0];
        }
        WorldEngine.INSTANCE = new WorldEngine(new File(configPath));
        WorldEngine.INSTANCE.init();
    }

    public WorldEngine(File configFile) {
        Gson gson = new Gson();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        LoggerFactory.getLogger(this.getClass()).info("WorldEngine Version: " + getClass().getPackage().getImplementationVersion());
        LoggerFactory.getLogger(this.getClass()).info("Reading config: " + configFile.getName());
        try {
            this.config = gson.fromJson(new FileReader(configFile), Config.class);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(this.getClass()).error("Could not load config file: ");
            e.printStackTrace();
            try {
                LoggerFactory.getLogger(this.getClass()).info("Writing new config.json");
                String configJson = prettyGson.toJson(new Config());
                BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
                writer.write(configJson);
                writer.close();
                LoggerFactory.getLogger(this.getClass()).info("Please edit config.json and restart server!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.exit(1);
        }
        this.serverMap = new HashMap<>();
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
        this.game = new Game();
        this.game.start();

        for (Config.ServerConfig serverConfig : this.config.getServers()) {
            LoggerFactory.getLogger(this.getClass()).info("Starting server #" + serverConfig.getName() + "...");
            PacketSocketIOListener listener = serverConfig.getListener();
            if (listener == null) {
                LoggerFactory.getLogger(this.getClass()).error("Could not start server #" + serverConfig.getName());
                continue;
            }
            SocketServer server = new SocketServer(serverConfig.getPort(), listener);
            server.start();
            this.serverMap.put(serverConfig.getName(), server);
        }

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

    public Map<String, SocketServer> getServerMap() {
        return this.serverMap;
    }

}
