package com.qijianguo.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.enums.AccountDetailType;
import com.qijianguo.game.model.po.AccountDetail;
import com.qijianguo.game.model.vo.AccountDetailReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface AccountDetailService extends IService<AccountDetail> {

    /**
     * 分页查询用户的积分详情
     * @param user
     * @param req
     * @return
     */
    IPage<AccountDetail> page(User user, AccountDetailReq req);

    /**
     * @param type
     * @return
     */
    List<AccountDetail> top(AccountDetailType type);

    /**
     * 是否存在指定类型的积分类型
     * @param type
     * @return
     */
    boolean exists(Integer userId, AccountDetailType type);

    IPage<AccountDetail> top(AccountDetailReq req);
}
