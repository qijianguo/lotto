package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountDetailReq;

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
