package com.yincheng.game.service.task;

public class TaskTest {

    public static void main(String[] args) {
        AbstractTaskOperate dailyAbstractTaskOperate = new DailyTaskOperate();
        dailyAbstractTaskOperate.execute(TaskType.match("login"));

        AbstractTaskOperate newcomerAbstractTaskOperate = new NewcomerTaskOperate();
        newcomerAbstractTaskOperate.execute(TaskType.match("register"));
    }
}
