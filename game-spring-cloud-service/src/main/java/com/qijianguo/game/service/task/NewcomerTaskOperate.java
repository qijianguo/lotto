package com.qijianguo.game.service.task;

/**
 * @author qijianguo
 */
public class NewcomerTaskOperate extends AbstractTaskOperate {

    @Override
    public Task create(TaskType taskType) {
        switch (taskType) {
            case NEWCOMER_REGISTER:
                return new RegisterTask();
        }
        return null;
    }
}
