package com.qijianguo.game.service.task;

/**
 * @author qijianguo
 */
public interface Task {

    /**
     * 执行之前
     */
    void before();

    /**
     * 执行任务
     * @return
     */
    boolean execute();

    /**
     * 执行之后
     */
    void after();
}





    /*private Integer id;

    private String name;

    private String type;

    private Integer count;

    private Date updateTime;

    private Date createTime;*/