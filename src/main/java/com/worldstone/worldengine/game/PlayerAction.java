package com.worldstone.worldengine.game;

import com.worldstone.worldengine.game.player.Player;

public abstract class PlayerAction implements Runnable {

    private Player player;

    public PlayerAction(Player player) {
        this.player = player;
    }

}
