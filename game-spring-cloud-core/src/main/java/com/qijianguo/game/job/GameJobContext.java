package com.qijianguo.game.job;

import com.qijianguo.game.model.po.Task;
import com.qijianguo.game.context.GameContext;

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
