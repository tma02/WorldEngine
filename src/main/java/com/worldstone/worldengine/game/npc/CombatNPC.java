package com.worldstone.worldengine.game.npc;

import com.worldstone.worldengine.game.combat.CombatParticipant;

public abstract class CombatNPC extends CombatParticipant {

    private int priority;

    public CombatNPC(int maxHealth, int priority) {
        super(maxHealth);
        this.priority = priority;
    }

    /**
     * executed by CombatSession every tick
     */
    public abstract void tickCombat();

    /**
     *
     * @return Combat turn priority
     */
    public int getCombatPriority() {
        return this.priority;
    }

}
