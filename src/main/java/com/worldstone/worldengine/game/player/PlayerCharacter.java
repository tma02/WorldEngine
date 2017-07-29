package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.game.item.ItemContainer;

import java.util.HashMap;

public class PlayerCharacter {

    private ItemContainer inventory;
    private String area;
    private String displayName;
    private HashMap<String, Integer> skillExpMap;

    public PlayerCharacter() {
        this.inventory = new ItemContainer();
        this.area = "nowhere";
        this.displayName = "...";
        this.skillExpMap = new HashMap<>();
    }

    public String getArea() {
        return this.area;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public HashMap<String, Integer> getSkillExpMap() {
        return skillExpMap;
    }
}
