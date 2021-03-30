package com.yincheng.game.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 下注配置
 * @author qijianguo
 */

public enum Bet {
    IGNORE;
    public enum Target {
        A,
        B,
        C,
        D,
        SUM,
    }


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
        _9
    }

    private static Map<String, Bet.Target> BET_MAP;
    private static Map<String, Bet.Mode> MODE_MAP;

    public static Map<String, Bet.Target> target() {
        if (BET_MAP == null) {
            BET_MAP = Arrays.stream(Target.values()).collect(Collectors.toMap(Bet.Target::name, Function.identity()));
        }
        return BET_MAP;
    }
    public static Map<String, Bet.Mode> mode() {
        if (MODE_MAP == null) {
            MODE_MAP = Arrays.stream(Bet.Mode.values()).collect(Collectors.toMap(Bet.Mode::name, Function.identity()));
        }
        return MODE_MAP;
    }

    public static Integer getTargetNum(Bet.Target target, List<Integer> result) {
        Integer num = -1;
        switch (target) {
            case A:
                num = result.size() > 1 ? result.get(0) : -1;
                break;
            case B:
                num = result.size() > 2 ? result.get(1) : -1;
                break;
            case C:
                num = result.size() > 3 ? result.get(2) : -1;
                break;
            case D:
                num = result.size() > 4 ? result.get(3) : -1;
                break;
            case SUM:
                num = result.size() > 0 ? result.stream().mapToInt(Integer::intValue).sum() : -1;
                break;
            default:
        }
        return num;
    }

}
