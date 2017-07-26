package com.worldstone.worldengine.game.world;

public class Area {

    private String name;
    private String description;

    /**
     * Constructs an object representing an area of the world.
     * - Contains a list of NPCs, objects, and players.
     * - Manages the triggers and events inside the area.
     * @param name Name (must not start with "instance_")
     */
    public Area(String name) {
        this.name = name;
        this.description = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
