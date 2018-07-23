package com.worldstone.worldengine.net.listener;

import com.worldstone.worldengine.net.Interserver;

import java.util.HashMap;
import java.util.Map;

public abstract class InterserverSocketIOListener extends PacketSocketIOListener {

    private Map<String, Interserver> sessionServerMap = new HashMap<>();

    public boolean isAuthenticated(String sessionId) {
        return this.sessionServerMap.containsKey(sessionId) && this.sessionServerMap.get(sessionId) != null;
    }

    public Interserver getInterserver(String sessionId) {
        return this.sessionServerMap.get(sessionId);
    }

    public void authenticateSession(String sessionId, Interserver server) {
        this.sessionServerMap.put(sessionId, server);
    }

    public void deauthenticateSession(String sessionId) {
        this.sessionServerMap.remove(sessionId);
    }

}
