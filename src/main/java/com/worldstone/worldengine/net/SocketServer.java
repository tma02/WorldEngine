package com.worldstone.worldengine.net;

import io.scalecube.socketio.SocketIOListener;
import io.scalecube.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServer {

    private static Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    private int port;
    private SocketIOServer server;
    private SocketIOListener listener;

    public SocketServer(int port, SocketIOListener listener) {
        this.port = port;
        this.listener = listener;
    }

    public void start() {
        server = SocketIOServer.newInstance(this.port);
        server.setListener(listener);
        server.start();
        SocketServer.LOGGER.info(listener.getClass().getSimpleName() + " started on port " + this.port);
    }

}
