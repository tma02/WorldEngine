package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.game.item.ItemContainer;

import java.util.HashMap;

public class Player {

    private ItemContainer inventory;
    private String area;
    private HashMap<String, Integer> skillExpMap;

    public Player() {
        this.inventory = new ItemContainer();
        this.area = "nowhere";
        this.skillExpMap = new HashMap<>();
    }

}
