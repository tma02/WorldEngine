package com.worldstone.worldengine.game.item;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {

    private static Map<String, Map<String, Object>> ITEM_ATTRIBUTE_PRESETS = new HashMap<>();

    enum ItemType {
        BORING,
        WEARABLE,
        CONSUMABLE
    }

    public static Item getItem(ItemType type) {
        Item item = new Item();
        switch (type) {
            case WEARABLE:
            case CONSUMABLE:
                item.getAttributes().put("max_durability", 0);
                item.getAttributes().put("durability", 0);
            case BORING:
                item.getAttributes().put("name", "");
                item.getAttributes().put("display_name", "");
                item.getAttributes().put("description", "");
                item.getAttributes().put("type", type.name());
                item.getAttributes().put("can_trade", false);
                break;
        }
        return item;
    }

    public static Item getItem(ItemType type, String presetName) {
        Item item = ItemFactory.getItem(type);
        if (ItemFactory.ITEM_ATTRIBUTE_PRESETS.containsKey(presetName)) {
            Map<String, Object> preset = ItemFactory.ITEM_ATTRIBUTE_PRESETS.get(presetName);
            for (String attributeKey : preset.keySet()) {
                item.getAttributes().put(attributeKey, preset.get(attributeKey));
            }
        }
        return item;
    }

    public static void registerPreset(String presetName, Map<String, Object> attributePreset) {
        if (ItemFactory.ITEM_ATTRIBUTE_PRESETS.containsKey("presetName")) {
            LoggerFactory.getLogger(ItemFactory.class).warn("Overriding item attribute preset for #" + presetName + ".");
        }
        ItemFactory.ITEM_ATTRIBUTE_PRESETS.put(presetName, attributePreset);
        LoggerFactory.getLogger(ItemFactory.class).info("Registered item attribute preset for #" + presetName + ".");
    }

}
