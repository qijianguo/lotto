package com.yincheng.game.service.task;

/**
 * @author qijianguo
 */
public class DailyTaskOperate extends AbstractTaskOperate {

    @Override
    public Task create(TaskType taskType) {
        switch (taskType) {
            case DAILY_LOGIN:
                return new LoginTask();
        }
        return null;
    }

}
