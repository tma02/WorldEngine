package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.game.Action;

public abstract class PlayerAction extends Action {

    private PlayerCharacter playerCharacter;

    public PlayerAction(PlayerCharacter playerCharacter, int delayTicks) {
        super(delayTicks);
        this.playerCharacter = playerCharacter;
    }

    public PlayerAction(PlayerCharacter playerCharacter) {
        this(playerCharacter, 0);
    }

    public PlayerCharacter getPlayerCharacter() {
        return this.playerCharacter;
    }

}
