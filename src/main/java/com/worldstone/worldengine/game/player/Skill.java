package com.worldstone.worldengine.game.player;

public class Skill {

    private String name;
    private String displayName;
    private String description;

    public Skill(String name, String displayName, String description) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

}
