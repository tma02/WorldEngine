package com.worldstone.worldengine;

import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.LoginListener;
import org.slf4j.LoggerFactory;

public class WorldEngine {

    public static WorldEngine INSTANCE;

    public static void main(String[] args) {
        WorldEngine.INSTANCE = new WorldEngine();
    }

    public WorldEngine() {
        LoggerFactory.getLogger(this.getClass()).info("Starting WorldEngine " + getClass().getPackage().getImplementationVersion() + "...");
        LoggerFactory.getLogger(this.getClass()).info("Starting Login server...");
        new SocketServer(5000, new LoginListener()).start();
    }

}
