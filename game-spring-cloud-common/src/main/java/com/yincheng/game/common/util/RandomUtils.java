package com.yincheng.game.common.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author qijianguo
 */
public class RandomUtils {

    private RandomUtils() {
    }

    public static Integer secureRandomNum() {
        SecureRandom instance = new SecureRandom();
        return instance.nextInt(10);
    }

    public static List<Integer> secureRandomNums(int size) {
        SecureRandom instance = new SecureRandom();
        IntStream ints = instance.ints(size, 0, 10);
        List<Integer> collect = ints.mapToObj(Integer::new).collect(Collectors.toList());
        return collect;
    }

}
