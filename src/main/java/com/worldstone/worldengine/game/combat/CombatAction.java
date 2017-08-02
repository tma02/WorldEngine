package com.worldstone.worldengine.game.combat;

import com.worldstone.worldengine.game.player.PlayerAction;
import com.worldstone.worldengine.game.player.PlayerCharacter;

public abstract class CombatAction extends PlayerAction {

    private int preparationDelay;
    private int finishDelay;
    private Phase phase;

    public CombatAction(PlayerCharacter playerCharacter, int delayTicks, int preparationDelay, int finishDelay) {
        super(playerCharacter, delayTicks);
        this.preparationDelay = preparationDelay;
        this.finishDelay = finishDelay;
        this.phase = Phase.PREPARATION;
    }

    public int getPreparationDelay() {
        return preparationDelay;
    }

    public int getFinishDelay() {
        return finishDelay;
    }

    public Phase getPhase() {
        return phase;
    }


    /**
     * Implement combat delays through PlayerAction tick delays
     */
    @Override
    public void run() {
        this.prepare();
        final CombatAction this_ = this;
        final PlayerAction finishAction = new PlayerAction(this.getPlayerCharacter(), this.getFinishDelay()) {
            @Override
            public void run() {
                this_.end();
            }
        };
        PlayerAction combatAction = new PlayerAction(this.getPlayerCharacter(), this.getPreparationDelay()) {
            @Override
            public void run() {
                this_.action();
                this_.getPlayerCharacter().setNextAction(finishAction);
            }
        };
        this.getPlayerCharacter().setNextAction(combatAction);
    }

    public abstract void prepare();

    public abstract void action();

    public abstract void end();

    public enum Phase {
        PREPARATION,
        ACTION,
        FINISH
    }

}
