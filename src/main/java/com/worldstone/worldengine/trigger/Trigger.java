package com.worldstone.worldengine.trigger;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class Trigger {

    private String name;
    private String eventName;

    public Trigger(String name, String eventName) {
        this.name = name;
        this.eventName = eventName;
    }

    public void onTrigger(Map<String, Object> attributes) {
        LoggerFactory.getLogger(this.getClass()).debug("#" + this.name + "!" + this.eventName + " triggered with attributes:");
        String attributesJson = new Gson().toJson(attributes);
        LoggerFactory.getLogger(this.getClass()).debug(attributesJson);
        this.resolve(attributes);
    }

    public abstract void resolve(Map<String, Object> attributes);

    public String getName() {
        return this.name;
    }

    public String getEventName() {
        return this.eventName;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Trigger) {
            return ((Trigger) object).getName().equals(this.getName());
        }
        return super.equals(object);
    }

}
