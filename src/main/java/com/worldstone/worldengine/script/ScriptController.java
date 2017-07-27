package com.worldstone.worldengine.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptController {

    private static ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

}
