package com.qijianguo.game.model.po;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 * @author qijianguo
 */
@Data
public class Role implements GrantedAuthority {

    private Integer id;

    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
