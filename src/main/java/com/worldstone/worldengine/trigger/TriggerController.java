package com.worldstone.worldengine.trigger;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerController {

    private static Map<String, List<Trigger>> TRIGGER_MAP = new HashMap<>();

    /**
     * Registers a trigger so that other classes can trigger them through the controller
     * @param trigger Trigger to register
     * @return false if the same trigger is already registered to the event, true otherwise
     */
    public static boolean registerTrigger(Trigger trigger) {
        TriggerController.TRIGGER_MAP.putIfAbsent(trigger.getEventName(), new ArrayList<>());
        List<Trigger> eventTriggers = TriggerController.TRIGGER_MAP.get(trigger.getEventName());
        if (eventTriggers.contains(trigger)) {
            LoggerFactory.getLogger(TriggerController.class).warn("#" + trigger.getName() + " already registered.");
            return false;
        }
        eventTriggers.add(trigger);
        LoggerFactory.getLogger(TriggerController.class).info("#" + trigger.getName() + " registered to event !" + trigger.getEventName() + ".");
        return true;
    }

    /**
     * Attempts to remove a registered trigger in the controller
     * @param trigger Trigger to remove
     * @return false if a trigger with the same name does not exist, true if it was removed
     */
    public static boolean unregisterTrigger(Trigger trigger) {
        return TriggerController.unregisterTrigger(trigger.getName(), trigger.getEventName());
    }

    /**
     * Attempts to remove a registered trigger in the controller
     * @param triggerName name of the Trigger to remove
     * @param eventName event name of the Trigger to remove
     * @return false if a trigger with the same name does not exist, true if it was removed
     */
    public static boolean unregisterTrigger(String triggerName, String eventName) {
        if (TriggerController.TRIGGER_MAP.containsKey(eventName)) {
            List<Trigger> eventTriggers = TriggerController.TRIGGER_MAP.get(eventName);
            if (eventTriggers.removeIf(eventTrigger -> eventTrigger.getName().equals(triggerName))) {
                LoggerFactory.getLogger(TriggerController.class).info("#" + triggerName + " unregistered from event !" + eventName + ".");
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static void triggerEvent(String eventName, Map<String, Object> attributes) {
        if (TriggerController.TRIGGER_MAP.containsKey(eventName)) {
            List<Trigger> eventTriggers = TriggerController.TRIGGER_MAP.get(eventName);
            for (Trigger trigger : eventTriggers) {
                trigger.onTrigger(attributes);
            }
        }
        else if (!eventName.equals("game_combat_tick")) {
            LoggerFactory.getLogger(TriggerController.class).warn("Triggered event !" + eventName + " has no registered triggers.");
        }
    }

    /**
     * Triggers an event with an empty attribute map
     * @param eventName name of the event to trigger
     */
    public static void triggerEvent(String eventName) {
        TriggerController.triggerEvent(eventName, new HashMap<>());
    }

}
