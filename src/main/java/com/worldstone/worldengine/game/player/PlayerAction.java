package com.worldstone.worldengine.game.player;

public abstract class PlayerAction implements Runnable, Comparable<PlayerAction> {

    private PlayerCharacter playerCharacter;
    private int delayTicks;

    public PlayerAction(PlayerCharacter playerCharacter, int delayTicks) {
        this.playerCharacter = playerCharacter;
        this.delayTicks = delayTicks;
    }

    public PlayerAction(PlayerCharacter playerCharacter) {
        this(playerCharacter, 0);
    }

    public PlayerCharacter getPlayerCharacter() {
        return this.playerCharacter;
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
