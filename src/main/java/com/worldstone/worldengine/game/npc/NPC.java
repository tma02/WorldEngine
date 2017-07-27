package com.worldstone.worldengine.game.npc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPC {

    private Map<String, Object> attributes;
    private UUID uuid;

    public NPC() {
        this.attributes = new HashMap<>();
        this.attributes.put("name", "");
        this.attributes.put("display_name", "");
        this.attributes.put("max_health", 1);
        this.attributes.put("health", 1);
        this.attributes.put("hostility", 0);
        this.uuid = UUID.randomUUID();
    }

}
