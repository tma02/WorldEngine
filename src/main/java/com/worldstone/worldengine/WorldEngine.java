package com.worldstone.worldengine;

import com.worldstone.worldengine.game.Game;
import com.worldstone.worldengine.net.SocketServer;
import com.worldstone.worldengine.net.listener.LoginListener;
import com.worldstone.worldengine.script.ScriptController;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WorldEngine {

    public static WorldEngine INSTANCE;

    private Game game;
    private SocketServer loginServer;

    public static void main(String[] args) {
        WorldEngine.INSTANCE = new WorldEngine();
        WorldEngine.INSTANCE.init();
    }

    public WorldEngine() { }

    public void init() {
        LoggerFactory.getLogger(this.getClass()).info("WorldEngine " + getClass().getPackage().getImplementationVersion());
        LoggerFactory.getLogger(this.getClass()).info("Starting game thread...");
        this.game = new Game();
        this.game.start();
        LoggerFactory.getLogger(this.getClass()).info("Starting Login server...");
        LoginListener loginListener = new LoginListener();
        loginListener.registerPacketActions();
        this.loginServer = new SocketServer(5000, loginListener);
        this.loginServer.start();

        ScriptController.runScripts(new File("scripts/"));
    }

    public Game getGame() {
        return this.game;
    }

}
