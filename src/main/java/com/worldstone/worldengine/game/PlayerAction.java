package com.worldstone.worldengine.game;

import com.worldstone.worldengine.game.player.Player;

public abstract class PlayerAction implements Runnable, Comparable<PlayerAction> {

    private Player player;
    private int delayTicks;

    public PlayerAction(Player player, int delayTicks) {
        this.player = player;
        this.delayTicks = delayTicks;
    }

    public PlayerAction(Player player) {
        this(player, 0);
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getDelayTicks() {
        return this.delayTicks;
    }

    public void tick() {
        this.delayTicks--;
    }

    @Override
    public int compareTo(PlayerAction other) {
        return -1;
    }

}
