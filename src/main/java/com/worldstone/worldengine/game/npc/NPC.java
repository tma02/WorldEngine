package com.worldstone.worldengine.game.npc;

import com.worldstone.worldengine.game.GameCharacter;

import java.util.UUID;

public class NPC extends GameCharacter {

    private UUID uuid;

    public NPC() {
        this.uuid = UUID.randomUUID();
    }

}
