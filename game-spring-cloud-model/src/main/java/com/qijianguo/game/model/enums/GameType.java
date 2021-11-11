package com.qijianguo.game.model.enums;

/**
 * @author qijianguo
 */

public enum GameType {

    _3D("3d"),

    _4D("4d"),

    _5D("5d"),
    ;

    private String name;

    GameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GameType match(String name) {
        for (GameType value : GameType.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
