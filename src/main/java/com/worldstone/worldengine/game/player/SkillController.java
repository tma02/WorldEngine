package com.worldstone.worldengine.game.player;

import java.util.HashMap;
import java.util.Map;

public class SkillController {

    private static Map<String, Skill> SKILL_MAP = new HashMap<>();

    public static void registerSkill(Skill skill) {
        SkillController.SKILL_MAP.put(skill.getName(), skill);
    }

}
