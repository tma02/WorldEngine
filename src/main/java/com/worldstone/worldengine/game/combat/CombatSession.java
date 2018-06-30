package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.npc.CombatNPC;
import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;

import java.util.*;

public class CombatSession {

    private Map<UUID, CombatTeam> teams;
    private CombatTeam[] teamTurnOrder;
    private int teamTurn;
    private UUID uuid;
    private Trigger tickTrigger;

    public CombatSession() {
        this.teams = new HashMap<>();
        this.uuid = UUID.randomUUID();
        this.teamTurn = 0;
    }

    public void addTeam(CombatTeam team) {
        this.teams.put(team.getUuid(), team);
    }

    public void addParticipant(CombatTeam team, CombatParticipant participant) {
        this.teams.get(team.getUuid()).add(participant);
    }

    public void start() {
        // populate team order array
        this.teamTurnOrder = this.teams.values().toArray(new CombatTeam[this.teams.values().size()]);
        Arrays.sort(this.teamTurnOrder, Comparator.comparing((CombatTeam team) -> team.getPriority()).reversed());

        // Register tick trigger
        final CombatSession this_ = this;
        this.tickTrigger = new Trigger("combat_session_tick#" + System.identityHashCode(this), "game_combat_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.tick();
            }
        };
        TriggerController.registerTrigger(this.tickTrigger);

        // TODO: net
    }

    public void newTurn() {
        this.teamTurn ++;
        this.teamTurn %= this.teams.size();
        // TODO: net
    }

    private void tick() {
        for (CombatTeam team : this.teams.values()) {
            team.tick();
        }
    }

    public void finish() {
        TriggerController.unregisterTrigger(this.tickTrigger);
        // TODO: net
    }

    public int getTeamTurn() {
        return teamTurn;
    }

    public CombatTeam getActiveTeam() {
        return this.teamTurnOrder[this.teamTurn];
    }

}
