package com.worldstone.worldengine.script;

import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptController {

    public static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    public static void runScripts(File scriptDir) {
        for (final File fileEntry : scriptDir.listFiles()) {
            if (fileEntry.isDirectory()) {
                ScriptController.runScripts(fileEntry);
            }
            else {
                if (!fileEntry.getName().startsWith("_") && fileEntry.getName().endsWith(".js")) {
                    LoggerFactory.getLogger(ScriptController.class).info("Running #" + fileEntry.getName());
                    try {
                        ScriptController.SCRIPT_ENGINE_MANAGER.getEngineByName("nashorn").eval(new FileReader(fileEntry));
                    } catch (ScriptException | FileNotFoundException e) {
                        LoggerFactory.getLogger(ScriptController.class).error("Exception trying to eval script #" + fileEntry.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
