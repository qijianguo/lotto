package com.yincheng.game.service.task;

import org.springframework.stereotype.Component;

/**
 * @author qijianguo
 */
@Component
public class LoginTask implements Task {

    @Override
    public void before() {
        System.out.println("===========LoginTask name");
    }

    @Override
    public boolean execute() {
        System.out.println("===========LoginTask execute");
        return false;
    }

    @Override
    public void after() {
        System.out.println("===========LoginTask after");
    }
}
