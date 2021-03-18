package com.yincheng.game.service.task;

public class RegisterTask implements Task {

    @Override
    public void before() {
        System.out.println("===========RegisterTask before");
    }


    @Override
    public boolean execute() {
        System.out.println("===========RegisterTask execute");
        return false;
    }

    @Override
    public void after() {
        System.out.println("===========RegisterTask after");
    }
}
