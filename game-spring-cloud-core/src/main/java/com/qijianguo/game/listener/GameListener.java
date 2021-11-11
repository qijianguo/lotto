package com.qijianguo.game.listener;

import com.qijianguo.game.context.GameContext;

/**
 * @author qijianguo
 */
public interface GameListener {

    /**
     * 执行开始前
     */
    void beforeStart(GameContext context);

    /**
     * 执行停止后
     */
    void afterStop(GameContext context);
}
