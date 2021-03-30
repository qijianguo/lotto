package com.yincheng.game.model.enums;

import java.util.ArrayList;
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
        FN,
        L2,
        L3,
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

    public static List<Integer> getTargetNum(Bet.Target target, List<Integer> result) {
        Integer num;
        List<Integer> nums = new ArrayList<>();
        switch (target) {
            case A:
            case FN:
                num = result.size() >= 1 ? result.get(0) : -1;
                nums.add(num);
                break;
            case B:
                num = result.size() >= 2 ? result.get(1) : -1;
                nums.add(num);
                break;
            case C:
                num = result.size() >= 3 ? result.get(2) : -1;
                nums.add(num);
                break;
            case D:
                num = result.size() >= 4 ? result.get(3) : -1;
                nums.add(num);
                break;
            case SUM:
                num = result.size() > 0 ? result.stream().mapToInt(Integer::intValue).sum() : -1;
                nums.add(num);
                break;
            case L2:
            case L3:
                nums.addAll(result);
                break;
            default:
                nums.add(-1);
        }
        return nums;
    }

}
