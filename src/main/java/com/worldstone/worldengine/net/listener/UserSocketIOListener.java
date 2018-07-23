package com.worldstone.worldengine.net.listener;

import com.worldstone.worldengine.database.User;
import io.scalecube.socketio.Session;

import java.util.HashMap;
import java.util.Map;

public abstract class UserSocketIOListener extends PacketSocketIOListener {

    private Map<String, User> sessionUserMap;

    public UserSocketIOListener() {
        this.sessionUserMap = new HashMap<>();
    }

    public boolean isAuthenticated(String sessionId) {
        return this.sessionUserMap.containsKey(sessionId) && this.sessionUserMap.get(sessionId) != null;
    }

    public User getUser(String sessionId) {
        return this.sessionUserMap.get(sessionId);
    }

    public void authenticateSession(String sessionId, User user) {
        this.sessionUserMap.put(sessionId, user);
    }

    public void deauthenticateSession(String sessionId) {
        this.sessionUserMap.remove(sessionId);
    }

    @Override
    public void onDisconnect(Session session) {
        super.onDisconnect(session);
        this.deauthenticateSession(session.getSessionId());
    }

}
