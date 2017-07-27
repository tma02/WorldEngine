package com.worldstone.worldengine.game.item;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {

    private static Map<String, Map<String, Object>> ITEM_ATTRIBUTE_PRESETS = new HashMap<>();

    public enum ItemType {
        BORING,
        WEARABLE,
        CONSUMABLE
    }

    /**
     * Gets a new Item with the specified type.
     * All items have 'name', 'display_name', 'description', 'type', and 'can_trade'
     * WEARABLE and CONSUMABLE have 'max_durability', and 'durability'
     * @param type
     * @return Item with proper attributes for its type
     */
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

    /**
     * Gets a new Item with the specified type and preset.
     * Overrides attributes from getItem(ItemType type) with attributes from the preset with name presetName.
     * @param type Item type
     * @param presetName Item preset name
     * @return Item with overwritten attributes
     */
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

    /**
     * Registers a preset to be used with getItem(ItemType type, String presetName)
     * - Will overwrite an existing preset with the same name
     * @param presetName name of the Item attribute preset
     * @param attributePreset Map of the attributes
     */
    public static void registerPreset(String presetName, Map<String, Object> attributePreset) {
        if (ItemFactory.ITEM_ATTRIBUTE_PRESETS.containsKey("presetName")) {
            LoggerFactory.getLogger(ItemFactory.class).warn("Overwriting item attribute preset for #" + presetName + ".");
        }
        ItemFactory.ITEM_ATTRIBUTE_PRESETS.put(presetName, attributePreset);
        LoggerFactory.getLogger(ItemFactory.class).info("Registered item attribute preset for #" + presetName + ".");
    }

}
