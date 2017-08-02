package com.worldstone.worldengine.game.npc;

import com.worldstone.worldengine.game.combat.CombatParticipant;

public abstract class CombatNPC extends CombatParticipant {

    public CombatNPC(int maxHealth) {
        super(maxHealth);
    }

    /**
     * executed by CombatSession every tick
     */
    public abstract void tickCombat();

}
