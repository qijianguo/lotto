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
        SecureRandom random = new SecureRandom();
        return random.nextInt(10);
    }

    public static List<Integer> secureRandomNums(int size) {
        SecureRandom random = new SecureRandom();
        IntStream ints = random.ints(size, 0, 10);
        List<Integer> collect = ints.mapToObj(Integer::new).collect(Collectors.toList());
        return collect;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            /*Integer randomNum = secureRandomSingleNum();
            System.out.println(randomNum + "---" + org.apache.commons.lang3.RandomUtils.nextInt(0, 10));*/
            List<Integer> integers = secureRandomNums(i + 1);
            List<Integer> integers2= secureRandomNums(i + 1);
            System.out.println(integers);
            System.out.println(integers2);
        }
    }

}
