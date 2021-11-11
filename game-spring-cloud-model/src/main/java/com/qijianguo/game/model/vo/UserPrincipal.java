package com.qijianguo.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
public class UserPrincipal implements Principal {

    private Integer userId;

    public UserPrincipal(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        if (userId != null) {
            return String.valueOf(userId);
        }
        return null;
    }
}
