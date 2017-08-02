package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.GameCharacter;
import com.worldstone.worldengine.trigger.TriggerController;

public class CombatParticipant extends GameCharacter {

    private int health;
    private int maxHealth;

    /**
     *
     * @param health initial health
     * @param maxHealth maximum health
     */
    public CombatParticipant(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    /**
     * Initializes both this.health and this.maxHealth to maxHealth
     * @param maxHealth
     */
    public CombatParticipant(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void hit(int damage) {
        this.health -= damage;
    }

    void stateCheck() {
        if (this.health <= 0) {
            TriggerController.triggerEvent("combat_participant_death#" + System.identityHashCode(this));
        }
    }

}
