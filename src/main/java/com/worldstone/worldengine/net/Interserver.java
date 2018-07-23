package com.worldstone.worldengine.net;

public class Interserver {

    private String type;

    private String name;

    private String ip;

    private int port;

    public Interserver(String type, String name, String ip, int port) {
        this.type = type;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return this.name;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

}
