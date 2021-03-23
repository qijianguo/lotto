package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountDetailReq;
import sun.jvm.hotspot.debugger.Page;

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

    IPage<AccountDetail> top(AccountDetailReq req);
}
