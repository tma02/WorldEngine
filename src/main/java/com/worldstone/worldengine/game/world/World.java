package com.worldstone.worldengine.game.world;

import com.worldstone.worldengine.trigger.Trigger;
import com.worldstone.worldengine.trigger.TriggerController;

import java.util.HashMap;
import java.util.Map;

public class World {

    private String name;
    private Map<String, Area> areaMap;

    public World(String name) {
        this.name = name;
        this.areaMap = new HashMap<>();
        final World this_ = this;
        TriggerController.registerTrigger(new Trigger("world_trigger", "game_tick") {
            @Override
            public void resolve(Map<String, Object> attributes) {
                this_.tick();
            }
        });
    }

    public void addArea(Area area) {
        this.areaMap.put(area.getName(), area);
    }

    public Area getArea(Area area) {
        return this.areaMap.get(area.getName());
    }

    public Area getArea(String areaName) {
        return this.areaMap.get(areaName);
    }

    public void tick() {

    }

}
