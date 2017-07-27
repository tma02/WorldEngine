package com.worldstone.worldengine.game;

import com.worldstone.worldengine.game.player.Player;

public abstract class PlayerAction implements Runnable, Comparable<PlayerAction> {

    private Player player;

    public PlayerAction(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public int compareTo(PlayerAction other) {
        return -1;
    }

}
