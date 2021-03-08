package com.yincheng.game.job;

import com.yincheng.game.context.GameContext;
import com.yincheng.game.model.po.Task;

/**
 * @author qijianguo
 */
public class GameJobContext extends GameContext {

    private Task nextTask;

    public static GameJobContext create(int gameId, Task nextTask) {
        GameJobContext context = new GameJobContext();
        context.setGameId(gameId);
        context.setNextTask(nextTask);
        return context;
    }

    public Task getNextTask() {
        return nextTask;
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
