package com.yincheng.game.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author qijianguo
 */
@Data
@AllArgsConstructor
public class User {

    private String username;

    private String token;
}