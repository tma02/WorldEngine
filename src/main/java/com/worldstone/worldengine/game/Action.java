package com.worldstone.worldengine.game;

public abstract class Action implements Runnable, Comparable<Action> {

    private int delayTicks;

    public Action(int delayTicks) {
        this.delayTicks = delayTicks;
    }

    public Action() {
        this.delayTicks = 0;
    }

    public int getDelayTicks() {
        return this.delayTicks;
    }

    public void tick() {
        this.delayTicks--;
    }

    @Override
    public int compareTo(Action other) {
        return -1;
    }

}
