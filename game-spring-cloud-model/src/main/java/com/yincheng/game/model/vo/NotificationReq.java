package com.yincheng.game.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationReq {

    private Integer userId;

    private String description;


}
