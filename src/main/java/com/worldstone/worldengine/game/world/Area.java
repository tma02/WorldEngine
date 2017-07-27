package com.worldstone.worldengine.game.world;

import com.worldstone.worldengine.game.npc.NPC;
import com.worldstone.worldengine.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Area {

    private String name;
    private String description;
    private List<NPC> npcs;
    private List<Player> players;

    /**
     * Constructs an object representing an area of the world.
     * - Contains a list of NPCs, objects, and players.
     * - Manages the triggers and events inside the area.
     * @param name Name (must not start with "instance_")
     */
    public Area(String name) {
        this.name = name;
        this.description = name;
        this.npcs = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
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
