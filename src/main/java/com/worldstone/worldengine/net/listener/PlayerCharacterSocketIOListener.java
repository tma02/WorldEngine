package com.worldstone.worldengine.net.listener;

import com.worldstone.worldengine.game.player.PlayerCharacter;

import java.util.HashMap;
import java.util.Map;

public abstract class PlayerCharacterSocketIOListener extends UserSocketIOListener {

    private Map<String, PlayerCharacter> sessionPlayerCharacterMap;

    public PlayerCharacterSocketIOListener() {
        super();
        this.sessionPlayerCharacterMap = new HashMap<>();
    }

    public PlayerCharacter getPlayerCharacter(String sessionId) {
        return this.sessionPlayerCharacterMap.get(sessionId);
    }

    public void assignCharacterToSession(String sessionId, PlayerCharacter playerCharacter) {
        this.sessionPlayerCharacterMap.put(sessionId, playerCharacter);
    }

}
