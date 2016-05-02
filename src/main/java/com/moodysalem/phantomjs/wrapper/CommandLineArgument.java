package com.moodysalem.phantomjs.wrapper;

import org.apache.commons.exec.CommandLine;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to pass arguments to PhantomJS executable
 */
public class CommandLineArgument {
    private final String template;
    private final Map<String, Object> keyVals;

    public CommandLineArgument(String template) {
        this(template, null);
    }

    public CommandLineArgument(String template, String key, Object val) {
        this(template, makeMap(key, val));
    }

    private static Map<String, Object> makeMap(String key, Object val) {
        Map<String, Object> objMap = new HashMap<>();
        objMap.put(key, val);
        return objMap;
    }

    public CommandLineArgument(String template, Map<String, Object> keyVals) {
        if (template == null || template.isEmpty()) {
            throw new IllegalArgumentException("Empty argument");
        }
        this.template = template;
        this.keyVals = keyVals;
    }

    public void apply(CommandLine cmdLine, Map<String, Object> argMap) {
        cmdLine.addArgument(template);
        if (keyVals != null) {
            for (String k : keyVals.keySet()) {
                Object v = keyVals.get(k);

                if (argMap.containsKey(k)) {
                    throw new IllegalStateException("Cannot overwrite substition map keys");
                }

                argMap.put(k, v);
            }
        }
    }
}
