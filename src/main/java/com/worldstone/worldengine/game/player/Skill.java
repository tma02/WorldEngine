package com.worldstone.worldengine.game.player;

import java.util.HashMap;
import java.util.Map;

public class Skill {

    private static Map<String, Skill> SKILL_MAP = new HashMap<>();

    private String name;
    private String displayName;
    private String description;

    public Skill(String name, String displayName, String description) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public static void registerSkill(Skill skill) {
        Skill.SKILL_MAP.put(skill.name, skill);
    }

}