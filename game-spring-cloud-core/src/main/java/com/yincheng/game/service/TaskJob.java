package com.yincheng.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author qijianguo
 */
@Component
public class TaskJob {

    @Autowired
    private TaskService taskService;

    @PostConstruct
    private void init() {

    }
}
