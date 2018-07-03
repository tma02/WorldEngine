package com.worldstone.worldengine;

import java.util.Map;

public class Config {

    private Map<String, Object> database;

    private String hashSalt;

    /**
     * Servers WorldEngine will try to launch with the specified type.
     * 1 is launch, 0 is do not launch
     *            Login Game Control
     *    +------+-----+-----+-----+
     *    |  7   |  1  |  1  |  1  |
     *    |  6   |  1  |  1  |  0  |
     *    |  5   |  1  |  0  |  1  |
     *    |  4   |  1  |  0  |  0  |
     *    |  3   |  0  |  1  |  1  |
     *    |  2   |  0  |  1  |  0  |
     *    |  1   |  0  |  0  |  1  |
     *    |  0   |  0  |  0  |  0  |
     *    +------+-----+-----+-----+
     */
    private int type;

    /**
     * [0] login, [1] game, [2] control
     */
    private int[] ports;

    public Map<String, Object> getDatabase() {
        return this.database;
    }

    public String getHashSalt() {
        return this.hashSalt;
    }

    public int getType() {
        return this.type;
    }

    public int[] getPorts() {
        return this.ports;
    }
}
