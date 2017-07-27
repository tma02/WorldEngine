package com.worldstone.worldengine.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptController {

    private static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

}
