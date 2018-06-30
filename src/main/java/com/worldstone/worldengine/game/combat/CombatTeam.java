package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.npc.CombatNPC;
import com.worldstone.worldengine.game.player.PlayerCharacter;

import java.util.ArrayList;
import java.util.UUID;

public class CombatTeam extends ArrayList<CombatParticipant> {

    private UUID uuid;

    public CombatTeam() {
        this.uuid = UUID.randomUUID();
    }

    public void tick() {
        for (CombatParticipant participant : this) {
            if (participant instanceof CombatNPC) {
                ((CombatNPC) participant).tickCombat();
            }
            participant.stateCheck();
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getUuidString() {
        return this.uuid.toString();
    }

    /**
     *
     * @return Sum turn priority for the team
     */
    public int getPriority() {
        int priority = 0;
        for (CombatParticipant participant : this) {
            priority += participant.getCombatPriority();
        }
        return priority;
    }

    /**
     *
     * @return whether this team has a PlayerCharacter in it
     */
    public boolean hasPlayerCharacter() {
        for (CombatParticipant participant : this) {
            if (participant instanceof PlayerCharacter) {
                return true;
            }
        }
        return false;
    }

}
