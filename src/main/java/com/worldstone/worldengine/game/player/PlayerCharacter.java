package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.database.User;
import com.worldstone.worldengine.game.combat.CombatAction;
import com.worldstone.worldengine.game.combat.CombatParticipant;
import com.worldstone.worldengine.game.item.ItemContainer;
import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PlayerCharacter extends CombatParticipant {

    private ItemContainer inventory;
    private User user;
    private String area;
    private String displayName;
    private String encodedDisplayName;
    private HashMap<String, Integer> skillExpMap;
    private PlayerAction nextAction;
    private boolean actionRanThisTick;
    private Trigger tickTrigger;

    /**
     * Initializes a player and registers a trigger using the user's email
     * Sets health and maxHealth to -1
     * Sets area to "nowhere"
     * @param user User who owns this character
     * @param displayName
     */
    public PlayerCharacter(User user, String displayName) {
        super(-1);
        this.user = user;
        this.displayName = displayName;
        try {
            this.encodedDisplayName = URLEncoder.encode(displayName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        this.inventory = new ItemContainer();
        this.area = "nowhere";
        this.skillExpMap = new HashMap<>();
        this.nextAction = null;
        this.actionRanThisTick = false;

        final PlayerCharacter this_ = this;
        this.tickTrigger = new Trigger("player_tick_trigger#" + this.user.getEmail(), "game_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.tick();
            }
        };
        TriggerController.registerTrigger(this.tickTrigger);
    }

    private void tick() {
        if (this.nextAction != null) {
            if (this.nextAction.getDelayTicks() <= 0) {
                this.actionRanThisTick = true;
                this.nextAction.run();
                if (this.actionRanThisTick) {
                    this.nextAction = null;
                }
            }
            else {
                this.nextAction.tick();
            }
        }
    }

    public void finish() {
        TriggerController.unregisterTrigger(this.tickTrigger);
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public HashMap<String, Integer> getSkillExpMap() {
        return skillExpMap;
    }

    public PlayerAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(PlayerAction nextAction) {
        this.nextAction = nextAction;
        this.actionRanThisTick = false;
    }

    public ItemContainer getInventory() {
        return this.inventory;
    }

    public int getCombatPriority() {
        return this.skillExpMap.get("dexterity");
    }

}
