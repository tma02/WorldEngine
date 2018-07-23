package com.worldstone.worldengine.net.listener;

import com.worldstone.worldengine.net.ServerInfo;
import io.scalecube.socketio.Session;

import java.util.HashMap;
import java.util.Map;

public abstract class InterserverSocketIOListener extends PacketSocketIOListener {

    private Map<String, ServerInfo> sessionServerMap = new HashMap<>();

    public boolean isAuthenticated(String sessionId) {
        return this.sessionServerMap.containsKey(sessionId) && this.sessionServerMap.get(sessionId) != null;
    }

    public ServerInfo getServerInfo(String sessionId) {
        return this.sessionServerMap.get(sessionId);
    }

    public void authenticateSession(String sessionId, ServerInfo server) {
        this.sessionServerMap.put(sessionId, server);
    }

    public void deauthenticateSession(String sessionId) {
        this.sessionServerMap.remove(sessionId);
    }

    @Override
    public void onDisconnect(Session session) {
        super.onDisconnect(session);
        this.deauthenticateSession(session.getSessionId());
    }

}
