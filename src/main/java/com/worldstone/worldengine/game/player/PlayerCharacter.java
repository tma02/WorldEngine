package com.worldstone.worldengine.game.player;

import com.worldstone.worldengine.game.item.ItemContainer;
import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PlayerCharacter {

    private ItemContainer inventory;
    private String area;
    private String displayName;
    private String encodedDisplayName;
    private HashMap<String, Integer> skillExpMap;
    private PlayerAction nextAction;
    private boolean actionRanThisTick;

    public PlayerCharacter(String displayName) {
        this.inventory = new ItemContainer();
        this.area = "nowhere";
        this.displayName = displayName;
        try {
            this.encodedDisplayName = URLEncoder.encode(displayName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.skillExpMap = new HashMap<>();
        this.nextAction = null;
        this.actionRanThisTick = false;

        final PlayerCharacter this_ = this;
        TriggerController.registerTrigger(new Trigger("player_tick_trigger#" + this.encodedDisplayName, "game_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.tick();
            }
        });
    }

    public void tick() {
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

}
