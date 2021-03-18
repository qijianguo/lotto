package com.yincheng.game.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Bet {

    A,
    B,
    C,
    D,
    SUM,

    _IGNORE;
    //public List<String> SINGLE_MODE = Arrays.asList(HIGH, LOW.name())


    public enum Mode {
        HIGH,
        LOW,
        ODD,
        EVEN,
        _1,
        _2,
        _3,
        _4,
        _5,
        _6,
        _7,
        _8,
        _9;
    }

    private static Map<String, Bet> BET_MAP;
    private static Map<String, Bet.Mode> MODE_MAP;
    public static Map<String, Bet> target() {
        if (BET_MAP == null) {
            BET_MAP = Arrays.stream(Bet.values()).collect(Collectors.toMap(Bet::name, Function.identity()));
        }
        return BET_MAP;
    }

    public static Map<String, Bet.Mode> mode() {
        if (MODE_MAP == null) {
            MODE_MAP = Arrays.stream(Bet.Mode.values()).collect(Collectors.toMap(Bet.Mode::name, Function.identity()));
        }
        return MODE_MAP;
    }

}
