package com.worldstone.worldengine.game.item;

public class ItemFactory {

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

}
