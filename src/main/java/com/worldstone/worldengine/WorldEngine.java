package com.worldstone.worldengine;

import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.LoginListener;
import org.slf4j.LoggerFactory;

public class WorldEngine {

    public static WorldEngine INSTANCE;

    private Game game;
    private SocketServer loginServer;

    public static void main(String[] args) {
        WorldEngine.INSTANCE = new WorldEngine();
    }

    public WorldEngine() {
        LoggerFactory.getLogger(this.getClass()).info("Starting WorldEngine " + getClass().getPackage().getImplementationVersion() + "...");
        this.game = new Game();
        this.game.start();
        LoggerFactory.getLogger(this.getClass()).info("Starting Login server...");
        LoginListener loginListener = new LoginListener();
        loginListener.registerPacketActions();
        this.loginServer = new SocketServer(5000, loginListener);
        this.loginServer.start();
    }

}
