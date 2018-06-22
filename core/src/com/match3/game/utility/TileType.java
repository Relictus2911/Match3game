package com.match3.game.utility;

import com.badlogic.gdx.math.MathUtils;

public enum TileType {
    NONE,
    ORANGE,
    BLUE,
    RED,
    GREEN,
    PURPLE,
    YELLOW,
    MATCH,
    REPLACE;

    public static TileType getRandom() {
        return values()[(int) (MathUtils.random(1,6))];
    }
}
