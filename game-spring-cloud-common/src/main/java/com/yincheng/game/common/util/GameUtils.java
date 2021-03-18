package com.yincheng.game.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameUtils {

    public List<Integer> result2List(String result) {
        return Arrays.stream(result.split(",")).map(Integer::new).collect(Collectors.toList());
    }



}
