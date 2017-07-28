package com.worldstone.worldengine.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptController {

    private static ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

    public static void runScripts(File scriptDir) {
        for (final File fileEntry : scriptDir.listFiles()) {
            if (fileEntry.isDirectory()) {
                ScriptController.runScripts(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                if (fileEntry.getName().endsWith(".js")) {
                    try {
                        ScriptController.SCRIPT_ENGINE.eval(new FileReader(fileEntry));
                    } catch (ScriptException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
