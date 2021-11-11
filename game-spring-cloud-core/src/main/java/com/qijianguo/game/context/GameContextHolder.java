package com.qijianguo.game.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author qijianguo
 */
public class GameContextHolder {

    private static final ThreadLocal<GameContext> threadContext = new TransmittableThreadLocal<>();

    public static GameContext get() {
        return threadContext.get();
    }

    public static void set(GameContext context) {
        threadContext.set(context);
    }

    public static void remove() {
        threadContext.remove();
    }


}
