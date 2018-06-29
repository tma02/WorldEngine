package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.npc.CombatNPC;
import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CombatSession {

    private List<CombatParticipant> participants;
    private UUID uuid;
    private Trigger tickTrigger;

    public CombatSession() {
        this.participants = new ArrayList<>();
        this.uuid = UUID.randomUUID();

        final CombatSession this_ = this;
        this.tickTrigger = new Trigger("combat_session_tick#" + System.identityHashCode(this), "game_combat_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.tick();
            }
        };
        TriggerController.registerTrigger(this.tickTrigger);
    }

    public void addParticipant(CombatParticipant participant) {
        this.participants.add(participant);
        //TODO: net stuff to notify
    }

    private void tick() {
        for (CombatParticipant participant : this.participants) {
            if (participant instanceof CombatNPC) {
                ((CombatNPC) participant).tickCombat();
            }
            participant.stateCheck();
        }
    }

    public void finish() {
        TriggerController.unregisterTrigger(this.tickTrigger);
    }

}
