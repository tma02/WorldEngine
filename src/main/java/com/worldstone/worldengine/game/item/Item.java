package com.worldstone.worldengine.game.item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Item {

    private Map<String, Object> attributes;
    private UUID uuid;

    public Item() {
        this.attributes = new HashMap<>();
        this.uuid = UUID.randomUUID();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public enum ItemType {
        BORING,
        WEARABLE,
        CONSUMABLE
    }

    public enum ItemSlot {
        NONE,
        HEAD,
        NECK,
        BACK,
        BODY,
        LEFT_HAND,
        RIGHT_HAND,
        RING,
        GLOVES,
        BELT,
        LEGS,
        FEET
    }

}
