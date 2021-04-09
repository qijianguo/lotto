package com.yincheng.game.job;

import com.yincheng.game.context.GameContext;
import com.yincheng.game.model.po.Task;

/**
 * @author qijianguo
 */
public class GameJobContext extends GameContext {

    public static GameJobContext create(int gameId, Task nextTask) {
        GameJobContext context = new GameJobContext();
        context.setGameId(gameId);
        context.setNextTask(nextTask);
        return context;
    }

}
