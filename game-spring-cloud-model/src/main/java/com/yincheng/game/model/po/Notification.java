package com.yincheng.game.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author qijianguo
 */
@Data
public class Notification {

    private Integer type;

    private String cover;

    private String title;

    private Integer credit;

    private String description;

    private Date time;

}
