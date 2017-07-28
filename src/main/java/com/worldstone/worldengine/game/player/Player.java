package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.game.item.ItemContainer;

import java.util.HashMap;

public class Player {

    private ItemContainer inventory;
    private String area;
    private String username;
    private String displayName;
    private HashMap<String, Integer> skillExpMap;

    public Player() {
        this.inventory = new ItemContainer();
        this.area = "nowhere";
        this.username = "";
        this.displayName = "...";
        this.skillExpMap = new HashMap<>();
    }

    public String getArea() {
        return this.area;
    }

}
