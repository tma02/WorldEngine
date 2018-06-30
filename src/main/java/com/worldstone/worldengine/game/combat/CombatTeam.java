package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.npc.CombatNPC;

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
}
