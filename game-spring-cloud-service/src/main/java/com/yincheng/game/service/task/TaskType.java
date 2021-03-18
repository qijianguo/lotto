package com.yincheng.game.service.task;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum TaskType {

    DAILY_LOGIN("login", "登录"),

    NEWCOMER_REGISTER("register", "注册"),
    ;

    private String type;

    private String desc;

    TaskType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static TaskType match(String type) {
        for (TaskType taskType : TaskType.values()) {
            if (Objects.equals(type, taskType.getType())) {
                return taskType;
            }
        }
        return null;
    }
}
