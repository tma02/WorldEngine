package com.worldstone.worldengine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.worldstone.worldengine.database.Database;
import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.UserSocketIOListener;
import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.script.ScriptController;
import com.worldstone.worldengine.trigger.TriggerController;
import org.slf4j.LoggerFactory;

import java.io.*;

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
                LoggerFactory.getLogger(this.getClass()).info("Please edit config.json then restart server!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.exit(1);
        }
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

        int type = config.getType();

        if ((type & 4) == 4) {
            LoggerFactory.getLogger(this.getClass()).info("Starting game thread...");
            this.game = new Game();
            this.game.start();
            LoggerFactory.getLogger(this.getClass()).info("Starting game server...");
            // TODO: start game server
        }
        if ((type & 2) == 2) {
            LoggerFactory.getLogger(this.getClass()).info("Starting login server...");
            LoginListener loginListener = new LoginListener();
            this.loginServer = new SocketServer(this.getConfig().getPorts()[0], loginListener);
            ((LoginListener) this.loginServer.getListener()).registerPacketActions();
            this.loginServer.start();
        }
        if ((type & 1) == 1) {
            LoggerFactory.getLogger(this.getClass()).info("Starting control server...");
            // TODO: start control server
        }
        if (type == 0) {
            LoggerFactory.getLogger(this.getClass()).info("No servers configured to start (type = 0)!");
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

}
