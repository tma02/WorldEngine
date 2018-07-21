package com.worldstone.worldengine;

import com.worldstone.worldengine.net.listener.PacketSocketIOListener;
import com.worldstone.worldengine.script.ScriptController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private Map<String, Object> database;

    private Map<String, Object> controlServer;

    private String hashSalt;

    private List<ServerConfig> servers;

    public Config() {
        this.database = new HashMap<>();
        this.database.put("host", "localhost");
        this.database.put("port", 5432);
        this.database.put("database", "database");
        this.database.put("username", "");
        this.database.put("password", "");
        this.controlServer = new HashMap<>();
        this.controlServer.put("host", "localhost");
        this.controlServer.put("port", 38194);
        this.controlServer.put("key", "");
        this.hashSalt = "";
        this.servers = new ArrayList<>();
        this.servers.add(new ServerConfig("login", "com.worldstone.worldengine.net.listener.LoginListener", 38192));
    }

    public Map<String, Object> getDatabase() {
        return this.database;
    }

    public String getHashSalt() {
        return this.hashSalt;
    }

    public List<ServerConfig> getServers() {
        return this.servers;
    }

    public class ServerConfig {

        private String name;

        private String listenerPath;

        private int port;

        public ServerConfig(String name, String listenerPath, int port) {
            this.name = name;
            this.listenerPath = listenerPath;
            this.port = port;
        }

        public String getName() {
            return this.name;
        }

        public PacketSocketIOListener getListener() {
            try {
                Class serverClass = Class.forName(this.listenerPath);
                return (PacketSocketIOListener) serverClass.newInstance();
            } catch (ClassNotFoundException e) {
                return (PacketSocketIOListener) ScriptController.evalScript(new File(this.listenerPath));
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getPort() {
            return this.port;
        }

    }

}
