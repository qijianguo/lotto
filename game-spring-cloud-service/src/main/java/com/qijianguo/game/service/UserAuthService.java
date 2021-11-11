package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.UserAuth;

import java.util.List;

/**
 * @author qijianguo
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 根据uniod查询
     * @param unionId
     * @return
     */
    UserAuth getByUnionId(String unionId);

    List<UserAuth> getUserAuths(String mode, String unionId);

    List<UserAuth> getByUser(Integer userId);

}
